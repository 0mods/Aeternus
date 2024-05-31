/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.FlyingAnimal
import net.minecraft.world.item.ItemDisplayContext
import org.joml.Quaternionf
import team._0mods.aeternus.api.client.models.GltfTree
import team._0mods.aeternus.api.client.models.ModelData
import team._0mods.aeternus.api.client.models.animations.AnimationType
import team._0mods.aeternus.api.client.models.animations.GLTFAnimationPlayer
import team._0mods.aeternus.api.client.models.animations.PlayMode
import team._0mods.aeternus.api.client.models.manager.*
import team._0mods.aeternus.api.client.utils.use
import team._0mods.aeternus.api.util.get
import team._0mods.aeternus.api.util.rl

open class GLTFEntityRenderer<T>(mgr: EntityRendererProvider.Context): EntityRenderer<T>(mgr) where T: LivingEntity, T: IAnimated {
    val iihr = mgr.itemInHandRenderer.apply {
        GLTFEntityUtil.itemRenderer = this
    }

    companion object {
        const val NO_MODEL = "%NO_MODEL%"
    }

    override fun getTextureLocation(entity: T): ResourceLocation = TextureManager.INTENTIONAL_MISSING_TEXTURE

    override fun render(
        entity: T,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight)

        val cap = entity[AnimatedEntityCapability::class]
        val modelPath = cap.model
        if (modelPath == NO_MODEL) return

        val model = GltfManager.getOrCreate(modelPath.rl)
        poseStack.pushPose()

        preRender(entity, cap, model.animationPlayer, poseStack)

        val lerpBRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot)
        poseStack.mulPose(Axis.YP.rotationDegrees(-lerpBRot))

        model.visuals = ::drawVisuals

        model.update(cap, entity.tickCount, partialTick)
        model.entityUpdate(entity, cap, partialTick)

        model.render(poseStack, ModelData(entity.offhandItem, entity.mainHandItem, iihr, entity), { r ->
            val result = cap.textures[r.path]?.let {
                it.rl
            } ?: r

            Minecraft.getInstance().textureManager.getTexture(result).id
        }, packedLight, OverlayTexture.pack(0, if (entity.hurtTime > 0 || !entity.isAlive) 3 else 10))

        cap.subModels.forEach { (n, c) ->
            model.nodes[n]?.let {
                poseStack.use {
                    poseStack.mulPoseMatrix(it.globalMatrix)
                    GLTFEntityUtil.render(entity, c, entity.tickCount, partialTick, poseStack, packedLight)
                }
            }
        }

        poseStack.popPose()
    }

    protected open fun drawVisuals(entity: LivingEntity, stack: PoseStack, node: GltfTree.Node, light: Int) {
        if ((node.name?.contains("left", ignoreCase = true) == true || node.name?.contains(
                "right",
                ignoreCase = true
            ) == true) &&
            node.name.contains("hand", ignoreCase = true) &&
            node.name.contains("item", ignoreCase = true)
        ) {
            val isLeft = node.name.contains("left", ignoreCase = true)
            val item =
                (if (isLeft) entity.getItemInHand(InteractionHand.OFF_HAND) else entity.getItemInHand(InteractionHand.MAIN_HAND))
                    ?: return

            stack.pushPose()
            stack.mulPose(Axis.XP.rotationDegrees(-90F))

            iihr.renderItem(
                entity,
                item,
                if (isLeft) ItemDisplayContext.THIRD_PERSON_LEFT_HAND else ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                isLeft,
                stack,
                Minecraft.getInstance().renderBuffers().bufferSource(),
                light
            )

            stack.popPose()
        }
    }

    private fun preRender(entity: T, capability: AnimatedEntityCapability, manager: GLTFAnimationPlayer, stack: PoseStack) {
        stack.mulPoseMatrix(capability.transform.matrix)
        stack.last().normal().mul(capability.transform.normalMatrix)
        stack.mulPose(Axis.YP.rotationDegrees(180f))
        updateAnimations(entity, capability, manager)
    }

    private fun updateAnimations(entity: T, capability: AnimatedEntityCapability, manager: GLTFAnimationPlayer) {
        val layers = capability.layers
        when {
            entity.hurtTime > 0 -> {
                val name = manager.typeToAnimationMap[AnimationType.HURT]?.name ?: return
                if (layers.any { it.animation == name }) {
                    layers.filter { it.animation == name }.forEach { it.time = 0 }
                    return
                }

                layers += AnimationLayer(
                    name,
                    LayerMode.ADD,
                    PlayMode.ONCE,
                    1.0f, fadeIn = 5
                )
            }

            entity.swinging -> {
                val name = manager.typeToAnimationMap[AnimationType.SWING]?.name ?: return
                if (layers.any { it.animation == name }) return

                layers += AnimationLayer(
                    name,
                    LayerMode.ADD,
                    PlayMode.ONCE,
                    1.0f, fadeIn = 5
                )
            }

            !entity.isAlive -> {
                val name = manager.typeToAnimationMap[AnimationType.DEATH]?.name ?: return
                if (layers.any { it.animation == name }) return

                layers += AnimationLayer(
                    name,
                    LayerMode.ADD,
                    PlayMode.LAST_FRAME,
                    1.0f, fadeIn = 5
                )
            }
        }
        manager.currentLoopAnimation = when {
            entity is FlyingAnimal && entity.isFlying -> AnimationType.FLY
            entity.isSleeping -> AnimationType.SLEEP
            entity.vehicle != null -> AnimationType.SIT
            entity.fallFlyingTicks > 4 -> AnimationType.FALL
            entity.deltaMovement.length() > 0.1 -> {
                when {
                    entity.isVisuallySwimming -> AnimationType.SWIM
                    entity.isShiftKeyDown -> AnimationType.WALK_SNEAKED
                    entity.deltaMovement.length() > 0.95f -> AnimationType.RUN
                    else -> AnimationType.WALK
                }
            }

            else -> AnimationType.IDLE
        }
    }

}