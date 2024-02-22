/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.neo.api.registry

import com.google.common.base.Suppliers
import com.google.common.base.Objects as GoogleObjects
import com.google.common.collect.*
import net.minecraft.core.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.*
import net.neoforged.bus.api.*
import net.neoforged.neoforge.registries.*
import org.apache.commons.lang3.mutable.*
import org.slf4j.LoggerFactory
import team._0mods.aeternus.api.registry.registries.*
import team._0mods.aeternus.api.registry.registries.impl.RegistrySupplierImpl
import team._0mods.aeternus.api.registry.registries.option.*
import team._0mods.aeternus.api.util.set
import team._0mods.aeternus.common.ModName
import team._0mods.aeternus.neo.api.bus.NeoEventBusHelper
import java.util.*
import java.util.function.*


class RegistryProviderImpl: AbstractRegistryProvider {
    companion object {
        private val logger = LoggerFactory.getLogger("${ModName}Registry")
        private val LISTENERS: Multimap<RegistryEntryId<*>, Consumer<*>> = HashMultimap.create()

        fun listen(key: ResourceKey<*>, id: ResourceLocation, listener: Consumer<*>) {
            LISTENERS[RegistryEntryId(key, id)] = listener
        }
    }

    override fun get(modid: String): RegistrarManager.RegistrarProvider = RegistrarProviderImpl(modid)

    class Data<T> {
        internal var registered = false
        internal val objs: MutableMap<ResourceLocation, Supplier<out T>> = linkedMapOf()

        fun register(registry: Registry<T>, loc: ResourceLocation, obj: Mutable<T>, reference: Supplier<out T>) {
            if (!registered) {
                objs[loc] = Supplier {
                    val value = reference.get()
                    obj.value = value
                    value
                }
            } else {
                val resourceKey = registry.key()
                val value = reference.get()
                Registry.register(registry, loc, value)
                obj.value = value

                val id = RegistryEntryId(resourceKey, loc)
                LISTENERS.get(id).forEach {
                    it as Consumer<Any>
                    it.accept(value!!)
                }
                LISTENERS.removeAll(id)
            }
        }
    }

    @JvmRecord
    data class RegistryEntryId<T>(val resourceKey: ResourceKey<T>, val id: ResourceLocation) {
        override fun toString(): String = "Registry Entry [${resourceKey.location()} / ${id}]"
    }

    class RegistrarProviderImpl(private val modId: String): RegistrarManager.RegistrarProvider {
        companion object {
            internal val customRegs: MutableMap<ResourceKey<Registry<*>>, Registrar<*>> = hashMapOf()
        }
        private val registry: MutableMap<ResourceKey<out Registry<*>>, Data<*>> = hashMapOf()
        private val listeners: Multimap<ResourceKey<Registry<*>>, Consumer<Registrar<*>>> = HashMultimap.create()
        internal var newRegistries: MutableList<Registry<*>>? = arrayListOf()

        init {
            NeoEventBusHelper.getModEventBus(modId).get().register(EventListener())
        }

        override fun <T> get(key: ResourceKey<Registry<T>>): Registrar<T> {
            val registry = BuiltInRegistries.REGISTRY.get(key.location()) as Registry<T>?
            if (registry != null)
                return get(registry)
            val customReg = customRegs[key as ResourceKey<Registry<*>>]
            if (customReg != null) return customReg as Registrar<T>
            throw IllegalArgumentException("Registry $key doesn't exist")
        }

        override fun <T> get(registry: Registry<T>): Registrar<T> = RegistrarImpl(modId, this.registry, registry)

        override fun <T> forRegistry(key: ResourceKey<Registry<T>>, consumer: Consumer<Registrar<T>>) {
            listeners.put(key as ResourceKey<Registry<*>>, consumer as Consumer<Registrar<*>>)
        }

        override fun <T> builder(type: Class<T>, registryKey: ResourceLocation): RegistrarBuilder<T> =
                RegistryBuilderWrapper(this, RegistryBuilder(ResourceKey.createRegistryKey(registryKey)))

        inner class EventListener {
            @SubscribeEvent
            fun handle(evt: RegisterEvent) {
                registry.entries.forEach {
                    if (it.key == evt.registryKey) registerFor(evt, it.key as ResourceKey<Registry<Any>>, it.value as Data<Any>)
                }
            }

            fun <T> registerFor(evt: RegisterEvent, resourceKey: ResourceKey<out Registry<T>>, data: Data<T>) {
                evt.register(resourceKey) { reg ->
                    data.registered = true
                    data.objs.entries.forEach {
                        val loc = it.key
                        val value = it.value.get()
                        reg.register(loc, value)

                        val registryEntryId = RegistryEntryId(resourceKey, loc)
                        LISTENERS[registryEntryId].forEach { cons ->
                            cons as Consumer<Any>
                            cons.accept(value!!)
                        }
                        LISTENERS.removeAll(registryEntryId)
                    }
                }

                data.objs.clear()
                val registry = get(evt.registry)
                listeners.entries().forEach {
                    if (it.key.location().equals(resourceKey.location()))
                        it.value.accept(registry)
                }
            }

            @SubscribeEvent(priority = EventPriority.LOWEST)
            fun handlePost(evt: RegisterEvent) {
                val reg = get(evt.registry)
                val toRemove: MutableList<RegistryEntryId<*>> = arrayListOf()

                LISTENERS.asMap().entries.forEach {
                    if (it.key.resourceKey == evt.registryKey) {
                        if (reg.contains(it.key.id)) {
                            val value = reg.get(it.key.id) as Any
                            it.value.forEach { cons ->
                                cons as Consumer<Any>
                                cons.accept(value)
                            }
                        } else logger.warn("Registry entry listened {} was not realized", it.key)
                    }
                }

                toRemove.forEach {
                    LISTENERS.removeAll(it)
                }
            }

            @SubscribeEvent
            fun handleEvent(evt: NewRegistryEvent) {
                if (newRegistries != null) {
                    newRegistries!!.forEach { evt.register(it) }

                    newRegistries = null
                }
            }
        }
    }

    class RegistryBuilderWrapper<T>(private val provider: RegistrarProviderImpl, private val builder: RegistryBuilder<T>): RegistrarBuilder<T> {
        private var syncToClients = false

        override fun build(): Registrar<T> {
            builder.sync(syncToClients)
            if (provider.newRegistries == null)
                throw IllegalStateException("Enable to create registries. Registries are already aggregated!");
            val registry = builder.create()
            val reg = provider.get(registry)
            provider.newRegistries!!.add(registry)

            RegistrarProviderImpl.customRegs[reg.key() as ResourceKey<Registry<*>>] = reg

            return reg
        }

        override fun option(option: RegistrarOption): RegistrarBuilder<T> {
            if (option == StandardRegistrarOption.SYNC_TO_CLIENTS)
                this.syncToClients = true
            else if (option is DefaultIdRegistrarOption) this.builder.defaultKey(option.defaultId)

            return this
        }
    }

    class RegistrarImpl<T>(
            private val modId: String,
            private val registry: MutableMap<ResourceKey<out Registry<*>>, Data<*>>,
            private val delegate: Registry<T>
    ): Registrar<T> {
        override fun delegate(id: ResourceLocation): RegistrySupplier<T> {
            val value: Supplier<T> = Suppliers.memoize { get(id) }
            return sup(id, this, BooleanSupplier { contains(id) }, value)
        }

        override fun get(id: ResourceLocation): T? = delegate.get(id)

        override fun byRawId(id: Int): T? = delegate.byId(id)

        override fun contains(id: ResourceLocation): Boolean = delegate.keySet().contains(id)

        override fun getIds(): Set<ResourceLocation> = delegate.keySet()

        override fun entrySet(): Set<Map.Entry<ResourceKey<T>, T>> = delegate.entrySet()

        override fun key(): ResourceKey<out Registry<T>> = delegate.key()

        override fun listen(id: ResourceLocation, consumer: Consumer<T>) {
            if (contains(id)) consumer.accept(get(id)!!)
            else listen(key(), id, consumer)
        }

        override fun getHolder(key: ResourceKey<T>): Holder<T>? = delegate.getHolder(key).orElse(null)

        override fun containsValue(obj: T): Boolean = delegate.getResourceKey(obj!!).isPresent

        override fun getKey(obj: T): Optional<ResourceKey<T>> = delegate.getResourceKey(obj!!)

        override fun getRawId(obj: T): Int = delegate.getId(obj)

        override fun getId(obj: T): ResourceLocation? = delegate.getKey(obj!!)

        override fun <E : T> register(id: ResourceLocation, supplier: Supplier<E>): RegistrySupplier<E> {
            val data: Data<T> = registry.computeIfAbsent(key()) { Data<T>() } as Data<T>
            val obj: Mutable<T> = MutableObject()
            data.register(delegate, id, obj, supplier)
            return sup(id, this as Registrar<E>, { obj.value != null }, obj::getValue)
        }

        override fun iterator(): Iterator<T> {
            TODO("Not yet implemented")
        }

        private fun <E: T> sup(
                id: ResourceLocation,
                registrar: Registrar<E>,
                isPresent: BooleanSupplier,
                obj: Supplier<T>
        ) = object : RegistrySupplierImpl<E> {
            var holderField: Holder<E>? = null

            override val holder: Holder<E>?
                get() {
                    if (holderField != null) return holderField
                    holderField = registrar.getHolder(id)
                    return holderField
                }
            override val registrarManager: RegistrarManager
                get() = RegistrarManager.get(modId)

            override val registrar: Registrar<E>
                get() = registrar

            override val registryId: ResourceLocation
                get() = delegate.key().location()

            override val id: ResourceLocation
                get() = id

            override val isPresent: Boolean
                get() = isPresent.asBoolean

            override fun get(): E {
                val value = obj.get() as E ?: throw NullPointerException("Value missing: $id@$registryId")
                return value
            }

            override fun hashCode(): Int {
                return GoogleObjects.hashCode(registryId, this.id)
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as RegistrySupplier<*>
                return other.registryId == registryId && other.id == id
            }

            override fun toString(): String = "$registryId@$id"
        }
    }
}
