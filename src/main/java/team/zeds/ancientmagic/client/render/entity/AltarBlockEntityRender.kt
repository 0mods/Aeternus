package team.zeds.ancientmagic.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.BlockPos
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.client.model.data.ModelData
import net.minecraftforge.fml.loading.FMLEnvironment
import team.zeds.ancientmagic.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.client.render.AMRenderTypes
import team.zeds.ancientmagic.init.AMManage
import team.zeds.ancientmagic.init.registries.AMRegister
import kotlin.math.*

class AltarBlockEntityRender(context: BlockEntityRendererProvider.Context): BlockEntityRenderer<AltarBlockEntity> {
    override fun render(
        blockEntity: AltarBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        xMouse: Int,
        yMouse: Int
    ) {
        val minecraft = Minecraft.getInstance()
        val level = minecraft.level ?: return

        val inv = blockEntity.getInv()
        val stack = if (inv.getStackInSlot(1).isEmpty) inv.getStackInSlot(0) else inv.getStackInSlot(1)

        if (!stack.isEmpty) {
            poseStack.pushPose()
            poseStack.translate(0.5, 1.1, 0.5)
            val scale = if (stack.item is BlockItem) 0.95f else 0.75f
            poseStack.scale(scale, scale, scale)
            val tick = System.currentTimeMillis() / 800.0
            poseStack.translate(0.0, sin(tick % (2 * PI)) * 0.65, 0.0)
            poseStack.mulPose(Axis.YP.rotationDegrees(((tick * 40.0) % 360).toFloat()))
            minecraft.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, xMouse, yMouse, poseStack, bufferSource, level, 0)
            poseStack.popPose()
        }

        if (!FMLEnvironment.production || AMManage.COMMON_CONFIG.enableEasyMode.get()) {
            val pos = blockEntity.blockPos
            val consumer = bufferSource.getBuffer(AMRenderTypes.MAGICAL_HOLOGRAM)

            poseStack.pushPose()
            poseStack.translate(-pos.x.toDouble(), -pos.y.toDouble(), -pos.z.toDouble())

            blockEntity.getPedestalPosition().forEach {
                drawer(minecraft, poseStack, it, AMRegister.ALTAR_PEDESTAL_BLOCK.get(), level, consumer)
            }
            blockEntity.getBricksPosition().forEach {
                drawer(minecraft, poseStack, it, Blocks.STONE_BRICKS, level, consumer)
            }
            blockEntity.getBricksWallPosition().forEach {
                drawer(minecraft, poseStack, it, Blocks.STONE_BRICK_WALL, level, consumer)
            }
            blockEntity.getCutWoodPosition().forEach { blockPos ->
                for (block in blockEntity.strippedWoods)
                    drawer(minecraft, poseStack, blockPos, block, level, consumer)
            }
            blockEntity.fireStonePosition().forEach {
                drawer(minecraft, poseStack, it, Blocks.SMOOTH_STONE, level, consumer)
            }

            poseStack.popPose()
        }
    }

    override fun shouldRenderOffScreen(blockEntity: AltarBlockEntity): Boolean {
        return true
    }

    private fun drawer(minecraft: Minecraft, poseStack: PoseStack, blockPos: BlockPos, block: Block, level: Level, consumer: VertexConsumer) {
        if (level.isEmptyBlock(blockPos)) {
            poseStack.pushPose()
            poseStack.translate(blockPos.x.toDouble(), blockPos.y.toDouble(), blockPos.z.toDouble())
            minecraft.blockRenderer.renderBatched(block.defaultBlockState(), blockPos, level, poseStack, consumer, false,
                level.getRandom(), ModelData.EMPTY, null)
            poseStack.popPose()
        }
    }
}