package team.zeds.ancientmagic.api.helper

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import kotlin.math.hypot

object BlockEntityHelper {
    @JvmStatic
    fun dispatchToPlayers(blockEntity: BlockEntity) {
        val level = blockEntity.level!!
        val packet = blockEntity.updatePacket!!

        val players = level.players()
        val pos = blockEntity.blockPos

        for (player: Player in players) {
            if (player is ServerPlayer) {
                if (isPlayerNearby(player.x, player.z, pos.x + 0.5, pos.z + 0.5))
                    player.connection.send(packet)
            }
        }
    }
    @JvmStatic
    fun dispatchToPlayers(level: Level, x: Int, y: Int, z: Int) {
        val blockEntity = level.getBlockEntity(BlockPos(x, y, z))!!
        dispatchToPlayers(blockEntity)
    }

    fun isPlayerNearby(x1: Double, z1: Double, x2: Double, z2: Double): Boolean = hypot(x1 - x2, z1 - z2) < 64;
}