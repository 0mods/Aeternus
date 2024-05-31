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
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import team._0mods.aeternus.api.client.models.manager.AnimatedEntityCapability
import team._0mods.aeternus.api.client.models.manager.IAnimated
import team._0mods.aeternus.api.util.get

open class GLTFEntityRenderer<T>(mgr: EntityRendererProvider.Context): EntityRenderer<T>(mgr) where T: LivingEntity, T: IAnimated {
    val iihr = mgr.itemInHandRenderer.apply {
        GLTFEntityUtil.itemRenderer = this
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
    }
}