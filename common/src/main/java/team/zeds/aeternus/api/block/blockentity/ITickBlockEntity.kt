package team.zeds.aeternus.api.block.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

@Deprecated(
        "Want to move to team._0mods.aeternus.api.block.blockentity.IBlockEntity",
        ReplaceWith("IBlockEntity", "team._0mods.aeternus.api.block.blockentity.IBlockEntity")
)
interface ITickBlockEntity<T> where T: BlockEntity? {
    fun tickOnClient(level: Level, pos: BlockPos, state: BlockState, entity: T) {}
    fun tickOnServer(level: Level, pos: BlockPos, state: BlockState, entity: T) {}
}