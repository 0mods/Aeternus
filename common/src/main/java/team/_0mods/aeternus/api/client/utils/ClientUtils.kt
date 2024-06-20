/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.utils

import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.world.entity.LivingEntity
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3d
import org.joml.Vector3f

fun drawLine(
    bufferbuilder: BufferBuilder, matrix: Matrix4f,
    from: Vector3d, to: Vector3d,
    r: Float, g: Float, b: Float, a: Float,
) {
    bufferbuilder
        .vertex(matrix, from.x.toFloat(), from.y.toFloat() - 0.1f, from.z.toFloat())
        .color(r, g, b, a)
        .endVertex()
    bufferbuilder
        .vertex(matrix, to.x.toFloat(), to.y.toFloat() - 0.1f, to.z.toFloat())
        .color(r, g, b, a)
        .endVertex()
}


fun LivingEntity.render(
    guiGraphics: GuiGraphics,
    x: Float,
    y: Float,
    scale: Float,
    mouseX: Float,
    mouseY: Float,
) {
    InventoryScreen.renderEntityInInventory(
        guiGraphics,
        x,
        y,
        scale,
        Vector3f(),
        Quaternionf(),
        Quaternionf(),
        this
    )
}

inline fun PoseStack.use(u: PoseStack.() -> Unit) {
    this.pushPose()
    u()
    this.popPose()
}
