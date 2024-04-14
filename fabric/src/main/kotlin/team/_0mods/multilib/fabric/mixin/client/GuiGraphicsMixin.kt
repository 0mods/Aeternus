package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner
import net.minecraft.world.item.ItemStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.ModifyVariable
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.client.TooltipEvent
import team._0mods.multilib.impl.TooltipColorContext
import team._0mods.multilib.impl.TooltipPositionContext

@Mixin(GuiGraphics::class)
abstract class GuiGraphicsMixin {
    @Unique
    private var tooltipPositionContext: ThreadLocal<TooltipPositionContext> =
        ThreadLocal.withInitial { TooltipPositionContext() }

    @Inject(
        method = ["renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V"],
        at = [At("HEAD")]
    )
    private fun preRenderTooltipItem(font: Font, stack: ItemStack, x: Int, y: Int, ci: CallbackInfo) {
        TooltipEvent.additionalContext().item = stack
    }

    @Inject(
        method = ["renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V"],
        at = [At("RETURN")]
    )
    private fun postRenderTooltipItem(font: Font, stack: ItemStack, x: Int, y: Int, ci: CallbackInfo) {
        TooltipEvent.additionalContext().item = null
    }

    @Inject(method = ["renderTooltipInternal"], at = [At("HEAD")], cancellable = true)
    private fun renderTooltip(
        font: Font,
        list: List<ClientTooltipComponent>,
        x: Int,
        y: Int,
        positioner: ClientTooltipPositioner,
        ci: CallbackInfo
    ) {
        if (list.isNotEmpty()) {
            val colorContext = TooltipColorContext.CONTEXT.get()
            colorContext.reset()
            val positionContext = tooltipPositionContext.get()
            positionContext.reset(x, y)
            val obj = this as Any
            if (TooltipEvent.RENDER_PRE.event.renderTooltip(obj as GuiGraphics, list, x, y).isFalse) {
                ci.cancel()
            } else {
                TooltipEvent.RENDER_MODIFY_COLOR.event.renderTooltip(obj, x, y, colorContext)
                TooltipEvent.RENDER_MODIFY_POS.event.renderTooltip(obj, positionContext)
            }
        }
    }

    @ModifyVariable(
        method = ["renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"],
        at = At(value = "HEAD"),
        ordinal = 0,
        argsOnly = true
    )
    private fun modifyTooltipX(original: Int): Int {
        return tooltipPositionContext.get().tooltipX
    }

    @ModifyVariable(
        method = ["renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"],
        at = At(value = "HEAD"),
        ordinal = 1,
        argsOnly = true
    )
    private fun modifyTooltipY(original: Int): Int {
        return tooltipPositionContext.get().tooltipY
    }
}