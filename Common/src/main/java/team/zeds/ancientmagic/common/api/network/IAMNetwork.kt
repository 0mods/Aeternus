package team.zeds.ancientmagic.common.api.network

import net.minecraft.server.level.ServerPlayer

interface IAMNetwork {
    fun <MSG> sendToServer(message: MSG)
    fun <MSG> sendToPlayer(message: MSG, player: ServerPlayer)
}