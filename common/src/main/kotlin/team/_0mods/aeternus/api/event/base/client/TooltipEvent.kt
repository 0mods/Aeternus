/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.common.impl.event.TooltipAdditionalContext

interface TooltipEvent {
    companion object {
        @JvmField val ITEM = EventFactory.createNoResult<Item>()
        @JvmField val RENDER_PRE = EventFactory.createEventResult<Render>()
        @JvmField val RENDER_MODIFY_POS = EventFactory.createNoResult<RenderModifyPosition>()
        @JvmField val RENDER_MODIFY_COLOR = EventFactory.createNoResult<RenderModifyColor>()

        @JvmStatic fun additionalContext() = TooltipAdditionalContext.get()
    }

    @ApiStatus.NonExtendable
    interface AdditionalContents {
        var item: ItemStack?
    }

    fun interface Item {
        fun append(stack: ItemStack, lines: List<Component>, flag: TooltipFlag)
    }

    fun interface Render {
        fun renderTooltip(graphics: GuiGraphics, texts: List<out ClientTooltipComponent>, x: Int, y: Int)
    }

    fun interface RenderModifyPosition {
        fun renderTooltip(graphics: GuiGraphics, context: PositionContext)
    }

    fun interface RenderModifyColor {
        fun renderTooltip(graphics: GuiGraphics, x: Int, y: Int, context: ColorContext)
    }

    interface PositionContext {
        var tooltipX: Int

        var tooltipY: Int
    }

    interface ColorContext {
        var backgroundColor: Int

        var outlineGradientTopColor: Int

        var outlineGradientBottomColor: Int
    }
}
