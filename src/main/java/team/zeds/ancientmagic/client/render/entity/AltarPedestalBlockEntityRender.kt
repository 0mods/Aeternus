package team.zeds.ancientmagic.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemDisplayContext
import team.zeds.ancientmagic.block.entity.AltarPedestalBlockEntity
import kotlin.math.PI
import kotlin.math.sin

class AltarPedestalBlockEntityRender(context: BlockEntityRendererProvider.Context): BlockEntityRenderer<AltarPedestalBlockEntity> {
    override fun render(
        blockEntity: AltarPedestalBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        xMouse: Int,
        yMouse: Int
    ) {
        val minecraft = Minecraft.getInstance()
        val level = minecraft.level ?: return

        val inv = blockEntity.getInv()
        val stack = inv.getStackInSlot(0)

        if (!stack.isEmpty) {
            poseStack.pushPose()
            poseStack.translate(0.5, 1.2, 0.5)
            val scale = if (stack.item is BlockItem) 0.95f else 0.75f
            poseStack.scale(scale, scale, scale)
            val tick = System.currentTimeMillis() / 800.0
            poseStack.translate(0.0, sin(tick % (2 * PI)) * 0.065, 0.0)
            poseStack.mulPose(Axis.YP.rotationDegrees(((tick * 40.0) % 360).toFloat()))
            minecraft.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, xMouse, yMouse, poseStack, bufferSource, level, 0)
            poseStack.popPose()
        }
    }
}