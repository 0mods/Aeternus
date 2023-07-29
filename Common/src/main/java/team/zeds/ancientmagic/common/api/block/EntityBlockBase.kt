package team.zeds.ancientmagic.common.api.block

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

@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
abstract class EntityBlockBase(properties: Properties): Block(properties), EntityBlock, IRunsOnTicker {
    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (level.isClientSide) clientTicker(level, state, type) else serverTicker(level, state, type)
    }

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
