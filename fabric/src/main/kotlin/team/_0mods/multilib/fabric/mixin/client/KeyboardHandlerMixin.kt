package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.KeyboardHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.screens.Screen
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.ModifyVariable
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.client.InputEvent
import team._0mods.multilib.event.base.client.ScreenInputEvent
import team._0mods.multilib.event.result.EventResult
import team._0mods.multilib.fabric.hooks.ScreenInputDelegate

@Mixin(KeyboardHandler::class)
class KeyboardHandlerMixin {
    @Shadow
    @Final
    private lateinit var minecraft: Minecraft

    @ModifyVariable(method = ["method_1458", "lambda\$charTyped\$5"], at = At("HEAD"), ordinal = 0, argsOnly = true)
    private fun wrapCharTypedFirst(screen: GuiEventListener): GuiEventListener {
        if (screen is ScreenInputDelegate) {
            return screen.ml_delInputs()
        }
        return screen
    }

    @ModifyVariable(method = ["method_1473", "lambda\$charTyped\$6"], at = At("HEAD"), ordinal = 0, argsOnly = true)
    private fun wrapCharTypedSecond(screen: GuiEventListener): GuiEventListener {
        if (screen is ScreenInputDelegate) {
            return screen.ml_delInputs()
        }
        return screen
    }

    @Inject(
        method = ["keyPress"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
            ordinal = 0
        )],
        cancellable = true
    )
    fun onKey(long_1: Long, int_1: Int, int_2: Int, int_3: Int, int_4: Int, info: CallbackInfo) {
        if (!info.isCancelled) {
            if (int_3 != 1 && int_3 != 2) {
                if (int_3 == 0) {
                    val result = ScreenInputEvent.KEY_RELEASED_PRE.event
                        .onRelease(minecraft, minecraft.screen!!, int_1, int_2, int_4)
                    if (result.isPresent) info.cancel()
                }
            } else {
                val result = ScreenInputEvent.KEY_PRESSED_PRE.event
                    .onPress(minecraft, minecraft.screen!!, int_1, int_2, int_4)
                if (result.isPresent) info.cancel()
            }
        }
    }

    @Inject(
        method = ["keyPress"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
            ordinal = 0,
            shift = At.Shift.AFTER
        )],
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    fun onKeyAfter(
        long_1: Long,
        int_1: Int,
        int_2: Int,
        int_3: Int,
        int_4: Int,
        info: CallbackInfo,
        f3Pressed: Boolean,
        screen: Screen,
        bls: BooleanArray
    ) {
        if (!info.isCancelled && !bls[0]) {
            val result: EventResult = if (int_3 != 1 && int_3 != 2) {
                ScreenInputEvent.KEY_RELEASED_POST.event.onRelease(minecraft, screen, int_1, int_2, int_4)
            } else {
                ScreenInputEvent.KEY_PRESSED_POST.event.onPress(minecraft, screen, int_1, int_2, int_4)
            }
            if (result.isPresent) info.cancel()
        }
    }

    @Inject(method = ["keyPress"], at = [At("RETURN")], cancellable = true)
    fun onRawKey(handle: Long, key: Int, scanCode: Int, action: Int, modifiers: Int, info: CallbackInfo) {
        if (handle == minecraft.window.window) {
            val result =
                InputEvent.KEY_PRESS.event.onPress(minecraft, key, scanCode, action, modifiers)
            if (result.isPresent) info.cancel()
        }
    }
}