@file:Suppress("UNCHECKED_CAST")

package team._0mods.aeternus.api.registry.registries

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.service.ServiceProvider
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

@ApiStatus.Internal
abstract class AbstractRegistryProviderGetter {
    abstract fun get(modid: String): RegistrarManager.RegistryProvider
}

class RegistrarManager private constructor(private val modId: String) {
    companion object {
        private val MANAGER = ConcurrentHashMap<String, RegistrarManager>()

        @JvmStatic
        fun get(modid: String) = MANAGER.computeIfAbsent(modid, ::RegistrarManager)

        @JvmStatic
        fun <T> getId(obj: T, fallback: ResourceKey<Registry<T>>?): ResourceLocation? {
            if (fallback == null)
                return null
            return getId(obj, BuiltInRegistries.REGISTRY.get(fallback.location()) as Registry<T>)
        }

        @JvmStatic
        fun <T> getId(obj: T, fallback: Registry<T>?): ResourceLocation? {
            if (fallback == null)
                return null
            return fallback.getKey(obj)
        }
    }

    private val provider = ServiceProvider.registry.registryProviderGetter.get(modId)

    fun <T> get(registry: ResourceKey<Registry<T>>) = provider.get(registry)

    fun <T> get(registry: Registry<T>) = provider.get(registry)

    fun <T> forRegistry(key: ResourceKey<Registry<T>>, callback: Consumer<Registrar<T>>) = provider.forRegistry(key, callback)

    @SafeVarargs
    fun <T> builder(registryId: ResourceLocation, vararg typeGetter: T): RegistrarBuilder<T> {
        if (typeGetter.isNotEmpty()) throw IllegalStateException("Array must be empty! Founded values: $typeGetter")
        return provider.builder(typeGetter.javaClass.componentType as Class<T>, registryId)
    }

    @ApiStatus.Internal
    interface RegistryProvider {
        fun <T> get(key: ResourceKey<Registry<T>>): Registrar<T>

        fun <T> get(registry: Registry<T>): Registrar<T>

        fun <T> forRegistry(key: ResourceKey<Registry<T>>, consumer: Consumer<Registrar<T>>)

        fun <T> builder(type: Class<T>, registryKey: ResourceLocation): RegistrarBuilder<T>
    }
}
