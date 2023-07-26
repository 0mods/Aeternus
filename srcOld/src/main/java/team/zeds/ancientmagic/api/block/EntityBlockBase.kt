package team.zeds.ancientmagic.api.block

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION", "UNCHECKED_CAST")
abstract class EntityBlockBase(properties: Properties) : Block(properties), EntityBlock {
    protected fun <A: BlockEntity, B: BlockEntity> ticker(
        typeA: BlockEntityType<B>,
        typeB: BlockEntityType<A>,
        ticker: BlockEntityTicker<in A>
    ): BlockEntityTicker<B>? = if (typeA === typeB) ticker as BlockEntityTicker<B> else null

    override fun use(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): InteractionResult {
        return super.use(blockState, level, blockPos, player, hand, hitResult)
    }

    override fun onRemove(
        oldState: BlockState,
        level: Level,
        blockPos: BlockPos,
        newState: BlockState,
        isMoving: Boolean
    ) {
        super.onRemove(oldState, level, blockPos, newState, isMoving)
    }
}