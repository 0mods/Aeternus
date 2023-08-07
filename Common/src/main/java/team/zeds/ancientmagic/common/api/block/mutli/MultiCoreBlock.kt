package team.zeds.ancientmagic.common.api.block.mutli

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import team.zeds.ancientmagic.common.api.block.EntityBlockBase
import team.zeds.ancientmagic.common.api.block.mutli.entity.MultiCoreBlockEntity

class MultiCoreBlock: EntityBlockBase<MultiCoreBlockEntity>({ pos, state-> MultiCoreBlockEntity(pos, state) },
    Properties.copy(Blocks.IRON_BLOCK).noOcclusion().strength(0.3f)) {
    @Deprecated("Deprecated in Java")
    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        level.getBlockEntity(pos)?.let { (it as MultiCoreBlockEntity).breakMultiBlock() }
        super.onRemove(state, level, pos, newState, isMoving)
    }

    @Deprecated("Deprecated in Java")
    override fun use(
        state: BlockState, level: Level, pos: BlockPos,
        player: Player, hand: InteractionHand, hit: BlockHitResult
    ): InteractionResult {
        val tile = level.getBlockEntity(pos) as MultiCoreBlockEntity
        tile.onOpen(player, pos)

        return super.use(state, level, pos, player, hand, hit)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("RenderShape.INVISIBLE", "net.minecraft.world.level.block.RenderShape")
    )
    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.INVISIBLE
    }

    override fun propagatesSkylightDown(state: BlockState, getter: BlockGetter, pos: BlockPos): Boolean {
        return true
    }

    @Deprecated("Deprecated in Java", ReplaceWith("1.0f"))
    override fun getShadeBrightness(state: BlockState, getter: BlockGetter, pos: BlockPos): Float {
        return 1.0f
    }
}