package team._0mods.aeternus.neo.init.capability

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.capabilities.EntityCapability
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.api.magic.research.player.PlayerResearch
import team._0mods.aeternus.api.util.rl

@Mod.EventBusSubscriber(modid = ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
object ANCapabilities {
    val playerResearch: EntityCapability<PlayerResearch, Player> = EntityCapability.create("$ModId:player_research".rl, PlayerResearch::class.java, Player::class.java)

    @JvmStatic
    @SubscribeEvent
    fun registerCaps(evt: RegisterCapabilitiesEvent) {
        evt.registerEntity(playerResearch, EntityType.PLAYER) { _, _ -> PlayerResearchCapability() }
    }
}