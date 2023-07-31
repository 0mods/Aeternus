package team.zeds.ancientmagic.fabric.event

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.MinecraftServer
import team.zeds.ancientmagic.common.event.AMCommonnessEvents
import team.zeds.ancientmagic.fabric.network.AMNetwork

class PlayerTick: ServerTickEvents.StartTick {
    override fun onStartTick(server: MinecraftServer)  {
        val players = server.playerList.players
        for (player in players) {
            if (AMNetwork.S2C_PLAYER_MAGIC.getBuf() != null) AMCommonnessEvents.playerTick(player)
        }
    }
}