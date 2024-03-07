/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.multilib.forge.registry

import com.google.common.base.Suppliers
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.registries.*
import org.slf4j.LoggerFactory
import team._0mods.multilib.registries.*
import team._0mods.multilib.registries.impl.RegistrySupplierImpl
import team._0mods.multilib.registries.option.DefaultIdRegistrarOption
import team._0mods.multilib.registries.option.RegistrarOption
import team._0mods.multilib.registries.option.StandardRegistrarOption
import team._0mods.multilib.util.set
import team._0mods.multilib.forge.bus.ForgeEventBusHelper
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import com.google.common.base.Objects as GoogleObjects

class RegistryProviderImpl: AbstractRegistryProvider {
    companion object {
        private val logger = LoggerFactory.getLogger("MultiLibRegistry")
        private val LISTENERS = HashMultimap.create<RegistryEntryId<*>, Consumer<*>>()

        @JvmStatic
        private fun listen(resourceKey: ResourceKey<*>, id: ResourceLocation, listener: Consumer<*>) {
            LISTENERS[RegistryEntryId(resourceKey, id)] = listener
        }
    }

    override fun get(modid: String): RegistrarManager.RegistrarProvider = RegistrarProviderImpl(modid)

    class Data<T> {
        internal var registered = false
        internal val objs: MutableMap<ResourceLocation, Supplier<out T>> = LinkedHashMap()

        fun registerForForge(registry: IForgeRegistry<T>, loc: ResourceLocation, objArr: Array<Any?>, reference: Supplier<out T>) {
            if (!registered)
                objs[loc] = Supplier {
                    val value = reference.get()
                    objArr[0] = value as Any
                    return@Supplier value
                }
            else {
                val resourceKey = ResourceKey.createRegistryKey<Registry<Any>>(registry.registryName)
                val value = reference.get()
                registry.register(loc, value)
                objArr[0] = value as Any

                val regEntry = RegistryEntryId(resourceKey, loc)
                LISTENERS.get(regEntry).forEach {
                    it as Consumer<Any>
                    it.accept(value)
                }
                LISTENERS.removeAll(regEntry)
            }
        }

        fun register(registry: Registry<T>, loc: ResourceLocation, reference: Supplier<out T>) {
            if (!registered)
                objs[loc] = reference
            else {
                val value = reference.get()
                Registry.register(registry, loc, value!!)

                val regEntry = RegistryEntryId(registry.key(), loc)
                LISTENERS.get(regEntry).forEach {
                    it as Consumer<Any>
                    it.accept(value as Any)
                }
                LISTENERS.removeAll(regEntry)
            }
        }
    }

    data class RegistryEntryId<T>(val registryKey: ResourceKey<T>, val id: ResourceLocation) {
        override fun toString(): String = "Registry Entry [${registryKey.location()} / $id]"
    }

    class RegistrarProviderImpl(internal val modId: String): RegistrarManager.RegistrarProvider {
        @JvmRecord
        data class RegistryBuilderEntry(val builder: RegistryBuilder<*>, val forgeRegistry: Consumer<IForgeRegistry<*>>)
        companion object {
            internal val customRegs: MutableMap<ResourceKey<Registry<*>>, Registrar<*>> = hashMapOf()
        }
        private val eventBus: Supplier<IEventBus>
        internal val registry: MutableMap<ResourceKey<out Registry<*>>, Data<*>> = hashMapOf()
        internal val listeners: Multimap<ResourceKey<Registry<*>>, Consumer<Registrar<*>>> = HashMultimap.create()

        internal var builders: MutableList<RegistryBuilderEntry>? = arrayListOf()

        init {
            eventBus = Suppliers.memoize {
                val bus = ForgeEventBusHelper.getModEventBus(modId).orElseThrow()
                bus.register(Listener())
                bus
            }
        }

        private fun updateBus() {
            synchronized(eventBus) {
                this.eventBus.get()
            }
        }

        override fun <T> get(key: ResourceKey<Registry<T>>): Registrar<T> {
            updateBus()
            val registry = RegistryManager.ACTIVE.getRegistry<T>(key.location())
            if (registry == null) {
                val reg = BuiltInRegistries.REGISTRY.get(key.location()) as Registry<T>?
                if (reg != null)
                    return get(reg)
                val customReg = customRegs[(key as ResourceKey<Registry<*>>)]
                if (customReg != null) return customReg as Registrar<T>
                throw IllegalArgumentException("Registry $key doesn't exist!")
            }
            return get(key)
        }

        fun <T> get(registry: IForgeRegistry<T>): Registrar<T> {
            updateBus()
            return ForgeRegistrar(modId, this.registry, registry)
        }

        override fun <T> get(registry: Registry<T>): Registrar<T> {
            updateBus()
            return VanillaRegistrar(modId, this.registry, registry)
        }

        override fun <T> forRegistry(key: ResourceKey<Registry<T>>, consumer: Consumer<Registrar<T>>) {
            this.listeners.put(key as ResourceKey<Registry<*>>, consumer as Consumer<Registrar<*>>)
        }

        override fun <T> builder(type: Class<T>, registryKey: ResourceLocation): RegistrarBuilder<T> =
            RegistryBuilderWrapper(this, RegistryBuilder<T>().setName(registryKey), registryKey)

        inner class Listener {
            @SubscribeEvent
            fun handleEvent(event: RegisterEvent) {
                this@RegistrarProviderImpl.registry.entries.forEach {
                    if (it.key.equals(event.registryKey))
                        registerFor(event, it.key as ResourceKey<Registry<Any>>, it.value as Data<Any>)
                }
            }

            fun <T : Any> registerFor(event: RegisterEvent, resourceKey: ResourceKey<out Registry<T>>, data: Data<T>) {
                event.register(resourceKey) { registry ->
                    data.registered = true

                    data.objs.entries.forEach {
                        val loc = it.key
                        val value = it.value.get()
                        registry.register(loc, value)
                        val regEntry = RegistryEntryId(resourceKey, loc)
                        LISTENERS.get(regEntry).forEach { cons ->
                            cons as Consumer<Any>
                            cons.accept(value)
                        }
                        LISTENERS.removeAll(regEntry)
                    }

                    data.objs.clear()

                    val registrar: Registrar<Any> = if (event.getForgeRegistry<Any>() != null)
                        get(event.getForgeRegistry<Any>()!!)
                    else if (event.getVanillaRegistry<Any>() != null)
                        get(event.getVanillaRegistry<Any>()!!)
                    else {
                        logger.error("Unable to find registry from RegisterEvent as both vanilla and forge registries are null! {}", event.registryKey)
                        return@register
                    }

                    listeners.entries().forEach {
                        if (it.key.location().equals(resourceKey.location()))
                            it.value.accept(registrar)
                    }
                }
            }

            @SubscribeEvent(priority = EventPriority.LOWEST)
            fun handleEventPost(event: RegisterEvent) {
                val registrar: Registrar<Any> = if (event.getForgeRegistry<Any>() != null)
                    get(event.getForgeRegistry()!!)
                else if (event.getVanillaRegistry<Any>() != null)
                    get(event.getVanillaRegistry()!!)
                else {
                    logger.error("Unable to find registry from RegisterEvent as both vanilla and forge registries are null! {}", event.registryKey)
                    return
                }

                val toRemove = arrayListOf<RegistryEntryId<*>>()

                LISTENERS.asMap().entries.forEach {
                    if (it.key.registryKey.equals(event.registryKey)) {
                        if (registrar.contains(it.key.id)) {
                            val value = registrar.get(it.key.id)!!
                            it.value.forEach { cons ->
                                cons as Consumer<Any>
                                cons.accept(value)
                            }
                            toRemove.add(it.key)
                        } else {
                            logger.warn("Registry entry won't released. {}", it.key)
                        }
                    }
                }

                toRemove.forEach {
                    LISTENERS.removeAll(it)
                }
            }

            @SubscribeEvent
            fun handle(event: NewRegistryEvent) {
                if (builders != null) {
                    builders!!.forEach {
                        val builder = it.builder
                        val fr = it.forgeRegistry

                        builder as RegistryBuilder<Any>
                        fr as Consumer<IForgeRegistry<Any>>

                        event.create(builder, fr)
                    }
                    builders = null
                }
            }
        }
    }

    class RegistryBuilderWrapper<T>(private val provider: RegistrarProviderImpl, private val builder: RegistryBuilder<*>, private val registryId: ResourceLocation): RegistrarBuilder<T> {
        private var syncToClients = false

        override fun build(): Registrar<T> {
            if (!syncToClients) builder.disableSync()
            builder.disableSaving()
            if (provider.builders == null)
                throw IllegalStateException("Cannot create registries when registries are already aggregated!")
            val registrarRef = arrayOfNulls<Registrar<*>>(1)
            val reg = DelegatedRegistrar(provider.modId, {
                Objects.requireNonNull(registrarRef[0], "Registry not yet initialized!")!!
            }, registryId) as DelegatedRegistrar<T>
            val entry = RegistrarProviderImpl.RegistryBuilderEntry(builder) {
                registrarRef[0] = provider.get(it)
                reg.onRegister()
            }
            provider.builders!!.add(entry)
            RegistrarProviderImpl.customRegs[reg.key() as ResourceKey<Registry<*>>] = reg
            return reg
        }

        override fun option(option: RegistrarOption): RegistrarBuilder<T> {
            if (option == StandardRegistrarOption.SYNC_TO_CLIENTS)
                this.syncToClients = true
            else if (option is DefaultIdRegistrarOption)
                this.builder.setDefaultKey(option.defaultId)
            return this
        }
    }

    class VanillaRegistrar<T>(private val modId: String, private var registry: MutableMap<ResourceKey<out Registry<*>>, Data<*>>, private var delegate: Registry<T>): Registrar<T> {
        override fun delegate(id: ResourceLocation): RegistrySupplier<T> {
            val value = Suppliers.memoize { get(id) }
            val registrar = this
            return object : RegistrySupplierImpl<T> {
                var holderField: Holder<T>? = null

                override val holder: Holder<T>?
                    get() {
                        if (holderField != null) return holderField
                        holderField = registrar.getHolder(id)
                        return holderField
                    }
                override val registrarManager: RegistrarManager = RegistrarManager.get(modId)

                override val registrar: Registrar<T> = registrar

                override val registryId: ResourceLocation = delegate.key().location()

                override val id: ResourceLocation = id

                override val isPresent: Boolean = contains(id)

                override fun getNullable(): T? = value.get()

                override fun hashCode(): Int {
                    return GoogleObjects.hashCode(registryId, this.id)
                }

                override fun equals(other: Any?): Boolean {
                    if (this === other) return true
                    if (javaClass != other?.javaClass) return false
                    other as RegistrySupplier<*>
                    return other.registryId == registryId && other.id == this.id
                }

                override fun toString(): String = "$registryId@$id"
            }
        }

        override fun get(id: ResourceLocation): T? = delegate.get(id)

        override fun byRawId(id: Int): T = delegate.byId(id)!!

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
            val data = registry.computeIfAbsent(key()) { Data<T>() } as Data<T>
            data.register(delegate, id, supplier)
            return delegate(id) as RegistrySupplier<E>
        }

        override fun iterator(): Iterator<T> = delegate.iterator()
    }

    class ForgeRegistrar<T>(private val modId: String, private var registry: MutableMap<ResourceKey<out Registry<*>>, Data<*>>, private var delegate: IForgeRegistry<T>): Registrar<T> {
        override fun delegate(id: ResourceLocation): RegistrySupplier<T> {
            val value = Suppliers.memoize { get(id) }
            val registrar = this
            return object : RegistrySupplierImpl<T> {
                var holderField: Holder<T>? = null

                override val holder: Holder<T>?
                    get() {
                        if (holderField != null) return holderField
                        holderField = registrar.getHolder(id)
                        return holderField
                    }
                override val registrarManager: RegistrarManager = RegistrarManager.get(modId)

                override val registrar: Registrar<T> = registrar

                override val registryId: ResourceLocation = delegate.registryName

                override val id: ResourceLocation = id

                override val isPresent: Boolean = contains(id)

                override fun getNullable(): T? = value.get()

                override fun hashCode(): Int {
                    return GoogleObjects.hashCode(registryId, this.id)
                }

                override fun equals(other: Any?): Boolean {
                    if (this === other) return true
                    if (javaClass != other?.javaClass) return false
                    other as RegistrySupplier<*>
                    return other.registryId == registryId && other.id == this.id
                }

                override fun toString(): String = "$registryId@$id"
            }
        }

        override fun get(id: ResourceLocation): T? = delegate.getValue(id)

        override fun byRawId(id: Int): T? = (delegate as ForgeRegistry<T>).getValue(id)

        override fun contains(id: ResourceLocation): Boolean = delegate.containsKey(id)

        override fun getIds(): Set<ResourceLocation> = delegate.keys

        override fun entrySet(): Set<Map.Entry<ResourceKey<T>, T>> = delegate.entries

        override fun key(): ResourceKey<out Registry<T>> = ResourceKey.createRegistryKey(delegate.registryName)

        override fun listen(id: ResourceLocation, consumer: Consumer<T>) {
            if (contains(id)) consumer.accept(get(id)!!)
            else listen(key(), id, consumer)
        }

        override fun getHolder(key: ResourceKey<T>): Holder<T>? = delegate.getHolder(key).orElse(null)

        override fun containsValue(obj: T): Boolean = delegate.containsValue(obj)

        override fun getKey(obj: T): Optional<ResourceKey<T>> = Optional.ofNullable(getId(obj)).map { ResourceKey.create(key(), it) }

        override fun getRawId(obj: T): Int = (delegate as ForgeRegistry<T>).getID(obj)

        override fun getId(obj: T): ResourceLocation? = delegate.getKey(obj)

        override fun <E : T> register(id: ResourceLocation, supplier: Supplier<E>): RegistrySupplier<E> {
            val objArray = arrayOf<Any?>(null)
            val data = registry.computeIfAbsent(key()) { Data<T>() } as Data<T>
            data.registerForForge(delegate, id, objArray, supplier)
            val registrar = this
            return object : RegistrySupplierImpl<E> {
                var holderField: Holder<E>? = null
                
                override val holder: Holder<E>?
                    get() {
                        if (holderField != null) return holderField
                        holderField = this.registrar.getHolder(this.id)
                        return holderField
                    }
                override val registrarManager: RegistrarManager = RegistrarManager.get(modId)

                override val registrar: Registrar<E> = registrar as Registrar<E>

                override val registryId: ResourceLocation = delegate.registryName

                override val id: ResourceLocation = id

                override val isPresent: Boolean = objArray[0] != null

                override fun getNullable(): E {
                    val value = objArray[0] as E ?: throw NullPointerException("Value missing: ${this.id}@$registryId.")
                    return value
                }

                override fun hashCode(): Int {
                    return GoogleObjects.hashCode(registryId, this.id)
                }

                override fun equals(other: Any?): Boolean {
                    if (this === other) return true
                    if (javaClass != other?.javaClass) return false
                    other as RegistrySupplier<*>
                    return other.registryId == registryId && other.id == this.id
                }

                override fun toString(): String = "$registryId@$id"
            }
        }

        override fun iterator(): Iterator<T> = delegate.iterator()
    }

    class DelegatedRegistrar<T>(private val modId: String, private val delegate: Supplier<Registrar<T>>, private val registryId: ResourceLocation): Registrar<T> {
        private var onRegister: MutableList<Runnable>? = arrayListOf()
        val isReady = onRegister == null

        fun onRegister() {
            if (onRegister != null) {
                for (run in onRegister!!) run.run()
            } else onRegister = null
        }

        override fun delegate(id: ResourceLocation): RegistrySupplier<T> {
            if (isReady) return delegate.get().delegate(id)
            return object : RegistrySupplierImpl<T> {
                var holderField: Holder<T>? = null

                override val holder: Holder<T>?
                    get() {
                        if (holderField != null || !isReady) return holderField
                        holderField = delegate.get().getHolder(id)
                        return holderField
                    }
                override val registrarManager: RegistrarManager = RegistrarManager.get(modId)

                override val registrar: Registrar<T> = this@DelegatedRegistrar

                override val registryId: ResourceLocation = this@DelegatedRegistrar.key().location()

                override val id: ResourceLocation = id

                override val isPresent: Boolean = isReady && contains(id)

                override fun getNullable(): T? = if (isReady) delegate.get().get(id)!! else null
            }
        }

        override fun get(id: ResourceLocation): T? = if (!isReady) null else delegate.get().get(id)

        override fun byRawId(id: Int): T? = if (!isReady) null else delegate.get().byRawId(id)

        override fun contains(id: ResourceLocation): Boolean = isReady && delegate.get().contains(id)

        override fun getIds(): Set<ResourceLocation> = if (isReady) delegate.get().getIds() else Collections.emptySet()

        override fun entrySet(): Set<Map.Entry<ResourceKey<T>, T>> = if (isReady) delegate.get().entrySet() else Collections.emptySet()

        override fun key(): ResourceKey<out Registry<T>> = if (isReady) delegate.get().key() else ResourceKey.createRegistryKey(registryId)

        override fun listen(id: ResourceLocation, consumer: Consumer<T>) {
            if (isReady) delegate.get().listen(id, consumer)
            else onRegister!!.add { delegate.get().listen(id, consumer) }
        }

        override fun getHolder(key: ResourceKey<T>): Holder<T>? = if (isReady) delegate.get().getHolder(key) else null

        override fun containsValue(obj: T): Boolean = isReady && delegate.get().containsValue(obj)

        override fun getKey(obj: T): Optional<ResourceKey<T>> = if (!isReady) Optional.empty() else delegate.get().getKey(obj)

        override fun getRawId(obj: T): Int = if (!isReady) -1 else delegate.get().getRawId(obj)

        override fun getId(obj: T): ResourceLocation? = if (!isReady) null else delegate.get().getId(obj)

        override fun <E : T> register(id: ResourceLocation, supplier: Supplier<E>): RegistrySupplier<E> {
            if (isReady) /* checks if onRegister == null */ return delegate.get().register(id, supplier)
            onRegister!!/* non-null cast. check the comment above it */.add { delegate.get().register(id, supplier) }
            return delegate(id) as RegistrySupplier<E>
        }

        override fun iterator(): Iterator<T> = if (isReady) delegate.get().iterator() else Collections.emptyIterator()
    }
}
