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
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import ru.hollowhorizon.hc.client.imgui.ImGuiHandler
import ru.hollowhorizon.hc.client.imgui.Renderable
import team._0mods.aeternus.platformredirect.api.client.screen.LayoutConsumer

class ImGuiContainerScreen<T: AbstractContainerMenu>(
    menu: T,
    playerInventory: Inventory,
    title: Component,
    private val rend: Renderable
) : AbstractContainerScreen<T>(menu, playerInventory, title), LayoutConsumer {
    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick)
        ImGuiHandler.drawFrame(rend)
    }

    override fun addLayoutWidget(widget: AbstractWidget) {
        this.addRenderableWidget(widget)
    }

    override fun x(): Int = 0

    override fun y(): Int = 0

    override fun width(): Int = this.width

    override fun height(): Int = this.height
}