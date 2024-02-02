package team._0mods.aeternus.forge.capability

import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken

object AFCapabilities {
    val playerResearch: Capability<PlayerResearchCapability> = CapabilityManager.get(object : CapabilityToken<PlayerResearchCapability>() {})
}