package team.zeds.ancientmagic.common.api.block

import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

interface IRunsOnTicker {
    @Suppress("UNCHECKED_CAST")
    fun <A: BlockEntity, B: BlockEntity> createTicker(tickerType: BlockEntityType<B>, modType: BlockEntityType<A>,
                                                      ticker: BlockEntityTicker<in A>): BlockEntityTicker<B>? {
        return if (tickerType == modType) ticker as BlockEntityTicker<B> else null
    }
    fun <T: BlockEntity> serverTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return null
    }

    fun <T: BlockEntity> clientTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return null
    }
}
