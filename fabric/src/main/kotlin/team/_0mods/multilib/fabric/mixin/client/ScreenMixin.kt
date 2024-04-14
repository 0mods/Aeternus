package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.client.hooks.ScreenAccess
import team._0mods.multilib.client.screen.ScreenAccessImpl
import team._0mods.multilib.event.base.client.ScreenEvent
import team._0mods.multilib.fabric.hooks.ScreenInputDelegate
import team._0mods.multilib.fabric.hooks.ScreenInputDelegate.DelegateScreen

@Mixin(Screen::class)
abstract class ScreenMixin: ScreenInputDelegate {
    @Unique
    private var access: ScreenAccessImpl? = null

    @Unique
    private var inputDelegate: Screen? = null

    @Unique
    private fun getAccess(): ScreenAccess {
        if (access == null) {
            return ScreenAccessImpl(this as Screen).also { access = it }
        }

        access!!.screen = this as Screen
        return access!!
    }

    override fun ml_delInputs(): Screen {
        if (inputDelegate == null) {
            inputDelegate = DelegateScreen(this as Screen)
        }
        return inputDelegate!!
    }

    @Inject(
        method = ["init(Lnet/minecraft/client/Minecraft;II)V"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;init()V")],
        cancellable = true
    )
    private fun preInit(minecraft: Minecraft, width: Int, height: Int, ci: CallbackInfo) {
        if (ScreenEvent.INIT_PRE.event.init(this as Screen, getAccess()).isFalse) {
            ci.cancel()
        }
    }

    @Inject(
        method = ["init(Lnet/minecraft/client/Minecraft;II)V"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;init()V", shift = At.Shift.AFTER)]
    )
    private fun postInit(ci: CallbackInfo) {
        ScreenEvent.INIT_POST.event.init(this as Screen, getAccess())
    }

    @Inject(
        method = ["rebuildWidgets"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;init()V")],
        cancellable = true
    )
    private fun preInit2(ci: CallbackInfo) {
        if (ScreenEvent.INIT_PRE.event.init(this as Screen, getAccess()).isFalse) {
            ci.cancel()
        }
    }

    @Inject(
        method = ["rebuildWidgets"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;init()V", shift = At.Shift.AFTER)]
    )
    private fun postInit2(ci: CallbackInfo) {
        ScreenEvent.INIT_POST.event.init(this as Screen, getAccess())
    }
}