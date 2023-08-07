package team.zeds.ancientmagic.common.api.block.mutli.client

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import team.zeds.ancientmagic.common.api.block.mutli.entity.MultiCoreBlockEntity
import team.zeds.ancientmagic.common.client.render.entity.AbstractBlockEntityRenderer

class MultiblockRenderer(context: BlockEntityRendererProvider.Context): AbstractBlockEntityRenderer<MultiCoreBlockEntity>(
    context
) {
    override fun render(blockEntity: MultiCoreBlockEntity, partialTick: Float, stack: PoseStack, buf: MultiBufferSource, xMouse: Int, yMouse: Int) {
        val facing: Direction = blockEntity.blockState.getValue(HorizontalDirectionalBlock.FACING)

        when(facing) {
            Direction.NORTH -> stack.translate(blockEntity.offset.x, blockEntity.offset.y, blockEntity.offset.z)
            Direction.SOUTH -> stack.translate(-blockEntity.offset.x, blockEntity.offset.y, -blockEntity.offset.z)
            Direction.EAST -> stack.translate(-blockEntity.offset.z, blockEntity.offset.y, blockEntity.offset.x)
            Direction.WEST -> stack.translate(blockEntity.offset.z, blockEntity.offset.y, -blockEntity.offset.x)
            else -> {}
        }
    }

    override fun shouldRenderOffScreen(blockEntity: MultiCoreBlockEntity): Boolean {
        return true
    }
}