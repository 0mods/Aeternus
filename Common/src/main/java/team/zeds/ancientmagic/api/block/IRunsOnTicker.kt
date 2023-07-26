package team.zeds.ancientmagic.api.block

import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType

interface IRunsOnTicker {
    fun <A: BlockEntity, B: BlockEntity> clientTicker(tickerType: BlockEntityType<B>, modType: BlockEntityType<A>,
                                                      ticker: BlockEntityTicker<in A>): BlockEntityTicker<B>?
    fun <A: BlockEntity, B: BlockEntity> serverTicker(tickerType: BlockEntityType<B>, modType: BlockEntityType<A>,
                                                      ticker: BlockEntityTicker<in A>): BlockEntityTicker<B>?
}