package team.zeds.ancientmagic.api.network

import net.minecraft.server.level.ServerPlayer

interface IAMNetwork {
    fun <MSG> sendToServer(message: MSG)
    fun <MSG> sendToPlayer(message: MSG, player: ServerPlayer)
}