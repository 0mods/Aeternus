/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.network.chat.Component
import team._0mods.aeternus.api.ui.Alignment
import team._0mods.aeternus.api.util.mcText
import team._0mods.aeternus.api.util.mcTranslate
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.mixin.accessors.ScreenAccessor

open class AeternusScreen(title: Component = "".mcText) : Screen(title), LayoutConsumer {
    lateinit var textureManager: TextureManager

    override fun init() {
        this.textureManager = Minecraft.getInstance().textureManager
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        (this as ScreenAccessor).children().forEach { it.mouseMoved(mouseX, mouseY) }
        super.mouseMoved(mouseX, mouseY)
    }

    fun getAlignmentPosX(align: Alignment, offsetX: Int, posWidth: Int, targetWidth: Int) =
        (posWidth * align.factorX() - targetWidth * align.factorX() + offsetX).toInt()

    fun getAlignmentPosX(align: Alignment, offsetX: Int, posWidth: Int, targetWidth: Int, size: Int) =
        (posWidth * align.factorX() - targetWidth * align.factorX() * size + offsetX).toInt()

    fun getAlignmentPosY(align: Alignment, offsetY: Int, posHeight: Int, targetHeight: Int) =
        (posHeight * align.factorY() - targetHeight * align.factorY() + offsetY).toInt()

    fun getAlignmentPosY(align: Alignment, offsetY: Int, posHeight: Int, targetHeight: Int, size: Int) =
        (posHeight * align.factorY() - targetHeight * align.factorY() * size + offsetY).toInt()

    fun addButtons(vararg widgets: AbstractWidget) {
        widgets.forEach {
            this.addRenderableWidget(it)
        }
    }

    fun bind(modId: String, path: String) {
        RenderSystem.setShaderTexture(0, "$modId:textures/$path".rl)
    }

    fun drawString(gg: GuiGraphics, str: String, align: Alignment, offsetX: Int, offsetY: Int, color: Int) {
        gg.drawString(
            this.font,
            str.mcTranslate,
            getAlignmentPosX(align, offsetX, this.width, this.font.width(str)),
            getAlignmentPosY(align, offsetY, this.height, this.font.lineHeight),
            color
        )
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double): Boolean {
        var value = false

        this.children().forEach { value = value || it.mouseDragged(mouseX, mouseY, button, dragX, dragY) }

        return value
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        var value = false

        this.children().forEach { value = value || it.mouseReleased(mouseX, mouseY, button) }

        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun addLayoutWidget(widget: AbstractWidget) {
        this.addRenderableWidget(widget)
    }

    override fun x(): Int = 0

    override fun y(): Int = 0

    override fun width(): Int = this.width

    override fun height(): Int = this.height
}