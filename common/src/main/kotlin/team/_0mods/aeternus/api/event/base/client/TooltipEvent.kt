package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.impl.event.TooltipAdditionalContext


interface TooltipEvent {
    companion object {

        @JvmStatic
        fun additionalContext() = TooltipAdditionalContext.get()
    }

    @ApiStatus.NonExtendable
    interface AdditionalContents {
        var item: ItemStack?
    }

    interface Item {
        fun append(stack: ItemStack, lines: List<Component>, flag: TooltipFlag)
    }

    interface RenderModifyPosition {
        fun renderTooltip(graphics: GuiGraphics, context: PositionContext)
    }

    
    interface RenderModifyColor {
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