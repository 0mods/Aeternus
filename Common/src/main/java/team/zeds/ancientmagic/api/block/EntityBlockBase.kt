package team.zeds.ancientmagic.api.block

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION", "UNCHECKED_CAST")
abstract class EntityBlockBase(properties: Properties): Block(properties), EntityBlock, IRunsOnTicker {
    override fun <A : BlockEntity, B : BlockEntity> clientTicker(
        tickerType: BlockEntityType<B>,
        modType: BlockEntityType<A>,
        ticker: BlockEntityTicker<in A>
    ): BlockEntityTicker<B>? = if (tickerType == modType) ticker as BlockEntityTicker<B> else null

    override fun <A : BlockEntity, B : BlockEntity> serverTicker(
        tickerType: BlockEntityType<B>,
        modType: BlockEntityType<A>,
        ticker: BlockEntityTicker<in A>
    ): BlockEntityTicker<B>? = if (tickerType == modType) ticker as BlockEntityTicker<B> else null

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): InteractionResult {
        return super.use(state, level, pos, player, hand, hitResult)
    }

    override fun onRemove(
        oldState: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        isMoving: Boolean
    ) {
        super.onRemove(oldState, level, pos, newState, isMoving)
    }
}