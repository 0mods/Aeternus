package team.zeds.ancientmagic.common.api.helper

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import kotlin.math.hypot

class BlockEntityHelper {
    fun sendToPlayers(blockEntity: BlockEntity) {
        val level = blockEntity.level!!
        val packet = blockEntity.updatePacket!!

        val players = level.players()
        val pos = blockEntity.blockPos

        for (player: Player in players) {
            if (player is ServerPlayer)
                if (isPlayerNearby(player.x, player.z, pos.x + 0.5, pos.z + 0.5))
                    player.connection.send(packet)
        }
    }

    fun sendToPlayers(level: Level, x: Int, y: Int, z: Int) {
        val blockEntity = level.getBlockEntity(BlockPos(x, y, z))!!
        sendToPlayers(blockEntity)
    }

    fun isPlayerNearby(x: Double, z: Double, x0: Double, z0: Double): Boolean = hypot(x - x0, z - z0) < 64;

    companion object {
        var instance: BlockEntityHelper? = null
            get() {
                if (field == null) instance = BlockEntityHelper()
                return field
            }
            private set
    }
}