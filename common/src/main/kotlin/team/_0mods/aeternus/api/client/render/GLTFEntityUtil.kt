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
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ItemInHandRenderer
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import org.joml.Quaternionf
import team._0mods.aeternus.api.client.models.GltfTree
import team._0mods.aeternus.api.client.models.ModelData
import team._0mods.aeternus.api.client.models.animations.SubModelPlayer
import team._0mods.aeternus.api.client.models.manager.GltfManager
import team._0mods.aeternus.api.client.models.manager.SubModel
import team._0mods.aeternus.api.client.utils.use
import team._0mods.aeternus.api.util.memorize
import team._0mods.aeternus.api.util.rl

object GLTFEntityUtil {
    lateinit var itemRenderer: ItemInHandRenderer

    fun render(
        e: LivingEntity,
        model: SubModel,
        tickCount: Int,
        partialTick: Float,
        stack: PoseStack,
        packedLight: Int
    ) {
        if (model.model == "%NO_MODEL%") return

        val realModel = GltfManager.getOrCreate(model.model.rl)

        realModel.visuals = ::drawVisuals

        stack.mulPoseMatrix(model.transform.matrix)
        SubModelPlayer.update(realModel, model, tickCount, partialTick)

        realModel.render(
            stack,
            ModelData(null, null, itemRenderer, null),
            { it: ResourceLocation ->
                val result = model.textures[it.path]?.let { it.rl } ?: it
                Minecraft.getInstance().textureManager.getTexture(result).id
            }.memorize(), packedLight, OverlayTexture.pack(0, if (e.hurtTime > 0 || !e.isAlive) 3 else 10)
        )

        model.subModels.forEach { (b, m) ->
            realModel.nodes[b]?.let {
                stack.use {
                    stack.mulPoseMatrix(it.globalMatrix)
                    render(e, m, tickCount, partialTick, stack, packedLight)
                }
            }
        }
    }

    private fun drawVisuals(entity: LivingEntity, stack: PoseStack, node: GltfTree.Node, light: Int) {
        if ((node.name?.contains("left", ignoreCase = true) == true || node.name?.contains(
                "right",
                ignoreCase = true
            ) == true) &&
            node.name.contains("hand", ignoreCase = true) &&
            node.name.contains("item", ignoreCase = true)
        ) {
            val isLeft = node.name.contains("left", ignoreCase = true)
            val item = (if (isLeft) entity.getItemInHand(InteractionHand.OFF_HAND) else entity.getItemInHand(
                InteractionHand.MAIN_HAND
            )) ?: return

            stack.pushPose()
            stack.mulPose(Quaternionf().rotateX(-90.0f * Mth.DEG_TO_RAD))

            itemRenderer.renderItem(
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
}
