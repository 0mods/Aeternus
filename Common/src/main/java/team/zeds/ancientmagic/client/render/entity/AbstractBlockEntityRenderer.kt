package team.zeds.ancientmagic.client.render.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.renderer.block.BlockRenderDispatcher
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.entity.EntityRenderDispatcher
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import team.zeds.ancientmagic.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.init.config.AMConfig

abstract class AbstractBlockEntityRenderer<T: BlockEntity>(
    context:BlockEntityRendererProvider.Context
) : BlockEntityRenderer<T> {
    val blockEntityRenderDispatcher: BlockEntityRenderDispatcher
    val blockRenderDispatcher: BlockRenderDispatcher
    val itemRender: ItemRenderer
    val entityRender: EntityRenderDispatcher
    val modelSet: EntityModelSet
    val font: Font
    val minecraft: Minecraft
    val client: AMConfig.Client
    val common: AMConfig.Common

    init {
        this.blockEntityRenderDispatcher = context.blockEntityRenderDispatcher
        this.blockRenderDispatcher = context.blockRenderDispatcher
        this.itemRender = context.itemRenderer
        this.entityRender = context.entityRenderer
        this.modelSet = context.modelSet
        this.font = context.font
        this.minecraft = Minecraft.getInstance()
        this.client = AMConfig.client!!
        this.common = AMConfig.common!!
    }

    override fun shouldRenderOffScreen(blockEntity: T): Boolean {
        return true
    }

    protected fun drawer(poseStack: PoseStack, blockPos: BlockPos, block: Block, level: Level, consumer: VertexConsumer) {
        if (level.isEmptyBlock(blockPos)) {
            poseStack.pushPose()
            poseStack.translate(blockPos.x.toDouble(), blockPos.y.toDouble(), blockPos.z.toDouble())
            this.minecraft.blockRenderer.renderBatched(block.defaultBlockState(), blockPos, level, poseStack, consumer, false,
                level.getRandom())
            poseStack.popPose()
        }
    }
}