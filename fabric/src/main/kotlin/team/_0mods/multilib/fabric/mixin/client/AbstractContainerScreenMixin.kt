package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.client.ScreenEvent

@Mixin(AbstractContainerScreen::class)
abstract class AbstractContainerScreenMixin(title: Component) : Screen(title) {
    @Inject(
        method = ["renderBackground"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V",
            ordinal = 0, shift = At.Shift.AFTER)]
    )
    fun renderBg(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float, ci: CallbackInfo) {
        val obj = this as Any
        ScreenEvent.RENDER_CONTAINER_BACKGROUND.event.render(obj as AbstractContainerScreen<*>, graphics, mouseX, mouseY, partialTick)
    }

    @Inject(
        method = ["render"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderLabels(Lnet/minecraft/client/gui/GuiGraphics;II)V",
            ordinal = 0, shift = At.Shift.AFTER)]
    )
    fun render(gp: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float, ci: CallbackInfo) {
        val obj = this as Any
        ScreenEvent.RENDER_CONTAINER_FOREGROUND.event.render(obj as AbstractContainerScreen<*>, gp, mouseX, mouseY, partialTick)
    }
}
