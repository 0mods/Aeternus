@file:Suppress("UNUSED", "UNUSED_PARAMETER")
package team._0mods.aeternus.forge.api.registry

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
import team._0mods.aeternus.api.registry.registries.*
import team._0mods.aeternus.api.registry.registries.option.RegistrarOption
import team._0mods.aeternus.common.ModName
import team._0mods.aeternus.forge.api.bus.ForgeEventBusHelper
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import kotlin.collections.LinkedHashMap


class RegistryProviderImpl: AbstractRegistryProvider {
    companion object {
        private val logger = LoggerFactory.getLogger("${ModName}Registry")
        private val LISTENERS = HashMultimap.create<RegistryEntryId<*>, Consumer<*>>()
    }

    override fun get(modid: String): RegistrarManager.RegistrarProvider = RegistrarProviderImpl(modid)

    class Data<T> {
        internal var registered = false
        internal val objs: MutableMap<ResourceLocation, Supplier<out T>> = LinkedHashMap()

        fun registerForForge(registry: IForgeRegistry<T>, loc: ResourceLocation, objArr: Array<Any>, reference: Supplier<out T>) {
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
                Registry.register(registry, loc, value)

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
        internal val eventBus: Supplier<IEventBus>
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
                throw IllegalArgumentException("Registry $reg doesn't exist!")
            }
            return get(key)
        }

        fun <T> get(registry: IForgeRegistry<T>): Registrar<T> {
            TODO()
        }

        override fun <T> get(registry: Registry<T>): Registrar<T> {
            TODO("Not yet implemented")
        }

        override fun <T> forRegistry(key: ResourceKey<Registry<T>>, consumer: Consumer<Registrar<T>>) {
            this.listeners.put(key as ResourceKey<Registry<*>>, consumer as Consumer<Registrar<*>>)
        }

        override fun <T> builder(type: Class<T>, registryKey: ResourceLocation): RegistrarBuilder<T> {
            TODO("Not yet implemented")
        }

        inner class Listener {
            @SubscribeEvent
            fun handleEvent(event: RegisterEvent) {
                this@RegistrarProviderImpl.registry.entries.forEach {
                    if (it.key.equals(event.registryKey))
                        registerFor(event, it.key as ResourceKey<out Registry<Any>>, it.value as Data<Any>)
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
                Objects.requireNonNull(registrarRef[0], "")!!
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
            return this
        }
    }

    class DelegatedRegistrar<T>(private val modId: String, private val delegate: Supplier<Registrar<T>>, private val registryId: ResourceLocation): Registrar<T> {
        fun onRegister() {}

        override fun delegate(id: ResourceLocation): RegistrySupplier<T> {
            TODO("Not yet implemented")
        }

        override fun get(id: ResourceLocation): T? {
            TODO("Not yet implemented")
        }

        override fun byRawId(id: Int): T {
            TODO("Not yet implemented")
        }

        override fun contains(id: ResourceLocation): Boolean {
            TODO("Not yet implemented")
        }

        override fun getIds(): Set<ResourceLocation> {
            TODO("Not yet implemented")
        }

        override fun entrySet(): Set<Map.Entry<ResourceKey<T>, T>> {
            TODO("Not yet implemented")
        }

        override fun key(): ResourceKey<out Registry<T>> {
            TODO("Not yet implemented")
        }

        override fun listen(id: ResourceLocation, consumer: Consumer<T>) {
            TODO("Not yet implemented")
        }

        override fun getHolder(key: ResourceKey<T>): Holder<T>? {
            TODO("Not yet implemented")
        }

        override fun containsValue(obj: T): Boolean {
            TODO("Not yet implemented")
        }

        override fun getKey(obj: T): Optional<ResourceKey<T>> {
            TODO("Not yet implemented")
        }

        override fun getRawId(obj: T): Int {
            TODO("Not yet implemented")
        }

        override fun getId(obj: T): ResourceLocation? {
            TODO("Not yet implemented")
        }

        override fun <E : T> register(id: ResourceLocation, supplier: Supplier<E>): RegistrySupplier<E> {
            TODO("Not yet implemented")
        }

        override fun iterator(): Iterator<T> {
            TODO("Not yet implemented")
        }
    }
}
