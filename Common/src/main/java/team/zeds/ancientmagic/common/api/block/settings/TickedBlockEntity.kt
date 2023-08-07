package team.zeds.ancientmagic.common.api.block.settings

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

interface TickedBlockEntity<T: BlockEntity?> {
    fun tickOnClient(level: Level, pos: BlockPos, tate: BlockState, blockEntity: T) {}

    fun tickOnServer(level: Level, pos: BlockPos, state: BlockState, blockEntity: T) {}
}
