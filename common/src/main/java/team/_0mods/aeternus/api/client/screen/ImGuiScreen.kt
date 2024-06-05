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

import net.minecraft.client.gui.GuiGraphics
import team._0mods.aeternus.api.client.imgui.ImguiHandler
import team._0mods.aeternus.api.client.imgui.Renderable
import team._0mods.aeternus.api.client.imgui.test

class ImGuiScreen(private val isPauseScreen: Boolean = true, private val rend: Renderable = test()): AeternusScreen() {
    var mouseClicked = false

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick)
        ImguiHandler.drawFrame(rend)
    }

    override fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        return super.charTyped(codePoint, modifiers)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        mouseClicked = true
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        mouseClicked = false
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun isPauseScreen(): Boolean {
        return isPauseScreen
    }
}
