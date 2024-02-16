package team._0mods.aeternus.forge.service

import team._0mods.aeternus.api.registry.registries.AbstractRegistryProvider
import team._0mods.aeternus.forge.api.registry.RegistryProviderImpl
import team._0mods.aeternus.service.core.RegistryHelper

class ForgeRegistryHelper: RegistryHelper {
    override val registryProvider: AbstractRegistryProvider
        get() = RegistryProviderImpl()
}