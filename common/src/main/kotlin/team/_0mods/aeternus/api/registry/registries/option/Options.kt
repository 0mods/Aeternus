package team._0mods.aeternus.api.registry.registries.option

import net.minecraft.resources.ResourceLocation

interface RegistrarOption

enum class StandardRegistrarOption: RegistrarOption {
    SYNC_TO_CLIENTS
}

@JvmRecord
data class DefaultIdRegistrarOption(val defaultId: ResourceLocation): RegistrarOption