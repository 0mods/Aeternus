/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.platformredirect.api.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import ru.hollowhorizon.hc.client.imgui.ImGuiHandler
import ru.hollowhorizon.hc.client.imgui.Renderable
import ru.hollowhorizon.hc.client.imgui.test

class AeternusImGuiScreen(private val isPauseScreen: Boolean = true, private val rend: Renderable = test()): Screen(Component.empty()) {
    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        this.renderBackground(guiGraphics, i, j, f)
        ImGuiHandler.drawFrame(rend)
    }

    override fun isPauseScreen(): Boolean {
        return isPauseScreen
    }
}
