package team._0mods.aeternus.forge.init.capability

import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import team._0mods.aeternus.api.magic.research.player.PlayerResearch

object AFCapabilities {
    val playerResearch: Capability<PlayerResearch> = CapabilityManager.get(object : CapabilityToken<PlayerResearch>() {})
}