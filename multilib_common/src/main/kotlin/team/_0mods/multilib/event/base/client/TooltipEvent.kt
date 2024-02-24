/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package team._0mods.multilib.event.base.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import org.jetbrains.annotations.ApiStatus
import team._0mods.multilib.event.core.EventFactory
import team._0mods.multilib.event.result.EventResult
import team._0mods.multilib.impl.TooltipAdditionalContext

interface TooltipEvent {
    companion object {
        @JvmField val ITEM = EventFactory.createNoResult<team._0mods.multilib.event.base.client.TooltipEvent.Item>()
        @JvmField val RENDER_PRE = EventFactory.createEventResult<team._0mods.multilib.event.base.client.TooltipEvent.Render>()
        @JvmField val RENDER_MODIFY_POS = EventFactory.createNoResult<team._0mods.multilib.event.base.client.TooltipEvent.RenderModifyPosition>()
        @JvmField val RENDER_MODIFY_COLOR = EventFactory.createNoResult<team._0mods.multilib.event.base.client.TooltipEvent.RenderModifyColor>()

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
        fun renderTooltip(graphics: GuiGraphics, texts: List<out ClientTooltipComponent>, x: Int, y: Int): EventResult
    }

    fun interface RenderModifyPosition {
        fun renderTooltip(graphics: GuiGraphics, context: team._0mods.multilib.event.base.client.TooltipEvent.PositionContext)
    }

    fun interface RenderModifyColor {
        fun renderTooltip(graphics: GuiGraphics, x: Int, y: Int, context: team._0mods.multilib.event.base.client.TooltipEvent.ColorContext)
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
