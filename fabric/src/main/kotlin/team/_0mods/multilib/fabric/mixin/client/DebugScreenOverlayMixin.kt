package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.gui.components.DebugScreenOverlay
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.client.ScreenEvent

@Mixin(DebugScreenOverlay::class)
class DebugScreenOverlayMixin {
    @Inject(method = ["getGameInformation"], at = [At("RETURN")])
    private fun getLeftTexts(cir: CallbackInfoReturnable<List<String>>) {
        ScreenEvent.DEBUG_LEFT.event.render(cir.returnValue)
    }

    @Inject(method = ["getSystemInformation"], at = [At("RETURN")])
    private fun getRightTexts(cir: CallbackInfoReturnable<List<String>>) {
        ScreenEvent.DEBUG_RIGHT.event.render(cir.returnValue)
    }
}