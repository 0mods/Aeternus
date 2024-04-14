package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.Constant
import org.spongepowered.asm.mixin.injection.ModifyConstant
import team._0mods.multilib.impl.TooltipColorContext

@Mixin(TooltipRenderUtil::class)
class TooltipRenderUtilMixin {
    @ModifyConstant(method = ["renderTooltipBackground"], constant = [Constant(intValue = -0xfeffff0)])
    private fun modifyTooltipBackgroundColor(original: Int): Int {
        return TooltipColorContext.CONTEXT.get().backgroundColor
    }

    @ModifyConstant(method = ["renderTooltipBackground"], constant = [Constant(intValue = 0x505000ff)])
    private fun modifyTooltipOutlineGradientTopColor(original: Int): Int {
        return TooltipColorContext.CONTEXT.get().outlineGradientTopColor
    }

    @ModifyConstant(method = ["renderTooltipBackground"], constant = [Constant(intValue = 0x5028007f)])
    private fun modifyTooltipOutlineGradientBottomColor(original: Int): Int {
        return TooltipColorContext.CONTEXT.get().outlineGradientBottomColor
    }
}