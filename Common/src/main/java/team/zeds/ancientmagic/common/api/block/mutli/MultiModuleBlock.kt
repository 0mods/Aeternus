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
import team.zeds.ancientmagic.common.api.block.mutli.entity.MultiModuleBlockEntity

class MultiModuleBlock(properties: Properties?) : EntityBlockBase<MultiModuleBlockEntity>(
    { pos, state -> MultiModuleBlockEntity(pos, state) }, properties?: Properties.copy(Blocks.STONE)
) {
    @Deprecated("Deprecated in Java", ReplaceWith("BlockRenderType.INVISIBLE", "net.minecraft.block.BlockRenderType"))
    override fun getRenderShape(p_149645_1_: BlockState): RenderShape {
        return RenderShape.INVISIBLE
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith(
            "super.use(state, level, pos, player, hand, hit)",
            "net.minecraft.block.Block"
        )
    )
    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult
    ): InteractionResult {
        val tile = level.getBlockEntity(pos) as MultiModuleBlockEntity

        if (tile.corePos == null) return super.use(state, level, pos, player, hand, hit)

        val coreTile = level.getBlockEntity(tile.corePos!!) as MultiCoreBlockEntity

        coreTile.onOpen(player, pos)

        return super.use(state, level, pos, player, hand, hit)
    }

    @Deprecated("Deprecated in Java")
    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        level.getBlockEntity(pos)?.let { (it as MultiModuleBlockEntity).breakMultiBlock() }
        super.onRemove(state, level, pos, newState, isMoving)
    }

    override fun propagatesSkylightDown(state: BlockState, getter: BlockGetter, pos: BlockPos): Boolean {
        return true
    }

    @Deprecated("Deprecated in Java", ReplaceWith("1.0F"))
    override fun getShadeBrightness(state: BlockState, getter: BlockGetter, pos: BlockPos): Float {
        return 1.0F
    }
}