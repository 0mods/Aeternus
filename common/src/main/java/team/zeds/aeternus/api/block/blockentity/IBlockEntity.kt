package team.zeds.aeternus.api.block.blockentity

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

interface IBlockEntity<T>: ITickBlockEntity<T> where T: BlockEntity? {
    fun onPlace(level: Level, state: BlockState, oldState: BlockState, isMoving: Boolean)

    fun onRemove(level: Level, state: BlockState, oldState: BlockState, isMoving: Boolean)

    fun onPlacedBy(level: Level, state: BlockState, placer: LivingEntity?, stack: ItemStack)
}