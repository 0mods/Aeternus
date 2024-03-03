/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *                                                                                   
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package team._0mods.multilib.fabric.registry

import com.google.common.base.Suppliers
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback
import net.minecraft.core.Holder
import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import team._0mods.multilib.registries.*
import team._0mods.multilib.registries.impl.RegistrySupplierImpl
import team._0mods.multilib.registries.option.DefaultIdRegistrarOption
import team._0mods.multilib.registries.option.RegistrarOption
import team._0mods.multilib.registries.option.StandardRegistrarOption
import team._0mods.multilib.util.set
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import com.google.common.base.Objects as GoogleObjects

class RegistryProviderImpl: AbstractRegistryProvider {
    companion object {
        private val LISTENERS: Multimap<RegistryEntryId<*>, Consumer<*>> = HashMultimap.create()
        private val LISTENED_REGISTRIES: MutableSet<ResourceKey<*>> = HashSet()

        @JvmStatic
        private fun listen(resourceKey: ResourceKey<*>, id: ResourceLocation, listener: Consumer<*>) {
            if (LISTENED_REGISTRIES.add(resourceKey)) {
                val registry = Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(resourceKey.location()), "Registry with resource key $resourceKey is not found")!!
                (RegistryEntryAddedCallback.event(registry) as Event<RegistryEntryAddedCallback<*>>).register { _, entryId, obj ->
                    val registryEntryId = RegistryEntryId(resourceKey, entryId)
                    LISTENERS.get(registryEntryId).forEach {
                        it as Consumer<Any>
                        it.accept(obj)
                    }
                    LISTENERS.removeAll(registryEntryId)
                }
            }

            LISTENERS[RegistryEntryId(resourceKey, id)] = listener
        }
    }

    override fun get(modid: String): RegistrarManager.RegistrarProvider = RegistrarProvider(modid)

    class RegistrarProvider(private val modId: String):
        RegistrarManager.RegistrarProvider {
        override fun <T> get(key: ResourceKey<Registry<T>>): Registrar<T> = RegistrarImpl(
            modId,
            Objects.requireNonNull(BuiltInRegistries.REGISTRY.get(key.registry())) as Registry<T>
        )

        override fun <T> get(registry: Registry<T>): Registrar<T> = RegistrarImpl(modId, registry)

        override fun <T> forRegistry(key: ResourceKey<Registry<T>>, consumer: Consumer<Registrar<T>>) {
            consumer.accept(get(key))
        }

        override fun <T> builder(type: Class<T>, registryKey: ResourceLocation): RegistrarBuilder<T> = RegistrarBuilderWrapper(modId, type, registryKey)
    }

    class RegistryEntryId<T>(private val regKey: ResourceKey<T>, private val id: ResourceLocation) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as RegistryEntryId<*>
            return Objects.equals(regKey, other.regKey) && Objects.equals(id, other.id)
        }

        override fun hashCode(): Int {
            return Objects.hash(regKey, id)
        }
    }

    class RegistrarBuilderWrapper<T>(private val modId: String, private val type: Class<T>, private val registryId: ResourceLocation): RegistrarBuilder<T> {
        private val apply: MutableList<Consumer<FabricRegistryBuilder<T, out MappedRegistry<T>>>> = arrayListOf()
        private var defaultId: ResourceLocation? = null

        override fun build(): Registrar<T> {
            val builder = if (defaultId == null)
                FabricRegistryBuilder.createSimple(type, registryId)
            else FabricRegistryBuilder.createDefaulted(type, registryId, defaultId)

            apply.forEach { it.accept(builder) }

            return RegistrarManager.get(modId).get(builder.buildAndRegister())
        }

        override fun option(option: RegistrarOption): RegistrarBuilder<T> {
            if (option == StandardRegistrarOption.SYNC_TO_CLIENTS)
                this.apply.add { it.attribute(RegistryAttribute.SYNCED) }
            else if (option is DefaultIdRegistrarOption)
                this.defaultId = option.defaultId
            return this
        }
    }

    class RegistrarImpl<T>(private val modId: String, private var delegate: Registry<T>): Registrar<T> {
        override fun delegate(id: ResourceLocation): RegistrySupplier<T> {
            val memorizedId = Suppliers.memoize { get(id) }
            val registrar = this

            return object : RegistrySupplierImpl<T> {
                private var holderVar: Holder<T>? = null

                override val holder: Holder<T>?
                    get() {
                        if (holderVar != null) return holderVar
                        holderVar = registrar.getHolder(id)
                        return holderVar
                    }
                override val registrarManager: RegistrarManager
                    get() = RegistrarManager.get(modId)
                override val registrar: Registrar<T>
                    get() = registrar
                override val registryId: ResourceLocation
                    get() = delegate.key().location()
                override val id: ResourceLocation
                    get() = id
                override val isPresent: Boolean
                    get() = contains(id)

                override fun getNullable(): T = memorizedId.get()!!

                override fun toString(): String {
                    return "$registryId@$id"
                }

                override fun hashCode(): Int {
                    return GoogleObjects.hashCode(registryId, this.id)
                }
            }
        }

        override fun get(id: ResourceLocation): T? = delegate.get(id)

        override fun byRawId(id: Int): T = delegate.byId(id)!!

        override fun contains(id: ResourceLocation): Boolean = delegate.keySet().contains(id)

        override fun getIds(): Set<ResourceLocation> = delegate.keySet()

        override fun entrySet(): Set<Map.Entry<ResourceKey<T>, T>> = delegate.entrySet()

        override fun key(): ResourceKey<out Registry<T>> = delegate.key()

        override fun listen(id: ResourceLocation, consumer: Consumer<T>) {
            if (contains(id))
                consumer.accept(get(id)!!)
            else
                listen(key(), id, consumer)
        }

        override fun getHolder(key: ResourceKey<T>): Holder<T>? = delegate.getHolder(key).orElse(null)

        override fun containsValue(obj: T): Boolean = delegate.getResourceKey(obj!!).isPresent

        override fun getKey(obj: T): Optional<ResourceKey<T>> = delegate.getResourceKey(obj!!)

        override fun getRawId(obj: T): Int = delegate.getId(obj)

        override fun getId(obj: T): ResourceLocation? = delegate.getKey(obj!!)

        override fun <E : T> register(id: ResourceLocation, supplier: Supplier<E>): RegistrySupplier<E> {
            Registry.register(delegate, id, supplier.get()!!)
            return delegate(id) as RegistrySupplier<E>
        }

        override fun iterator(): Iterator<T> = delegate.iterator()
    }
}
