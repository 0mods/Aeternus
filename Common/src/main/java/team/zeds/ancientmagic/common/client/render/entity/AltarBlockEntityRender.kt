package team.zeds.ancientmagic.common.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.level.block.Blocks
import team.zeds.ancientmagic.common.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.common.client.render.type.AMRenderTypes
import team.zeds.ancientmagic.common.platform.AMServices
import kotlin.math.PI
import kotlin.math.sin

class AltarBlockEntityRender(context: BlockEntityRendererProvider.Context) : AbstractBlockEntityRenderer<AltarBlockEntity>(
    context
) {
    override fun render(
        blockEntity: AltarBlockEntity,
        pTick: Float,
        poseStack: PoseStack,
        buf: MultiBufferSource,
        xMouse: Int,
        yMouse: Int
    ) {
        val level = this.minecraft.level ?: return // Returns void if level = null
        val inv = blockEntity.getInv()
        val stack = if (inv.getStackInSlotHandler(1).isEmpty) inv.getStackInSlotHandler(0) else inv.getStackInSlotHandler(1)

        if (!stack.isEmpty) {
            poseStack.pushPose()
            poseStack.translate(0.5, 1.1,0.5)
            val scale = if (stack.item is BlockItem) 0.95f else 0.75f
            poseStack.scale(scale, scale, scale)
            val tick = System.currentTimeMillis() / 800.0
            poseStack.translate(0.0, sin(tick % (2 * PI)) * 0.65, 0.0)
            poseStack.mulPose(Axis.YP.rotationDegrees(((tick * 40.0) % 360).toFloat()))
            minecraft.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, xMouse, yMouse, poseStack, buf, level, 0)
            poseStack.popPose()
        }

        if (AMServices.PLATFORM.isDeveloperment() || this.common.isEasyMod()) {
            val pos = blockEntity.blockPos
            val consumer = buf.getBuffer(AMRenderTypes.MAGICAL_HOLOGRAM)

            poseStack.pushPose()
            poseStack.translate(-pos.x.toDouble(), -pos.y.toDouble(), -pos.z.toDouble())

            blockEntity.getPedestalPosition().forEach {
                drawer(poseStack, it, AMServices.PLATFORM.getIAMRegistryEntry().getAltarPedestalBlock(), level, consumer)
            }
            blockEntity.getBricksPosition().forEach {
                drawer(poseStack, it, Blocks.STONE_BRICKS, level, consumer)
            }
            blockEntity.getBricksWallPosition().forEach {
                drawer(poseStack, it, Blocks.STONE_BRICK_WALL, level, consumer)
            }
            blockEntity.getCutWoodPosition().forEach { blockPos ->
                for (block in blockEntity.strippedWoods)
                    drawer(poseStack, blockPos, block, level, consumer)
            }
            blockEntity.fireStonePosition().forEach {
                drawer(poseStack, it, Blocks.SMOOTH_STONE, level, consumer)
            }

            poseStack.popPose()
        }
    }
}