package team._0mods.aeternus.api.registry.registries

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

interface DeferredSupplier<T>: OptionalSupplier<T> {
    val registryId: ResourceLocation

    val registryKey: ResourceKey<Registry<T>>
        get() = ResourceKey.createRegistryKey(registryId)

    val id: ResourceLocation

    val key: ResourceKey<T>
        get() = ResourceKey.create(registryKey, id)
}
