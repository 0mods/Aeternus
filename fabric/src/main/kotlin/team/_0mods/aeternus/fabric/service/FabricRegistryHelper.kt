package team._0mods.aeternus.fabric.service

import team._0mods.aeternus.api.registry.registries.AbstractRegistryProviderGetter
import team._0mods.aeternus.fabric.api.registry.RegistryProviderImpl
import team._0mods.aeternus.service.core.RegistryHelper

class FabricRegistryHelper: RegistryHelper {
    override val registryProviderGetter: AbstractRegistryProviderGetter
        get() = RegistryProviderImpl()
}