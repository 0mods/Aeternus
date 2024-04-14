package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.Minecraft
import net.minecraft.client.MouseHandler
import net.minecraft.client.gui.screens.Screen
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.Redirect
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.client.InputEvent
import team._0mods.multilib.event.base.client.ScreenInputEvent

@Mixin(MouseHandler::class)
class MouseHandlerMixin {
    @Shadow
    @Final
    private val minecraft: Minecraft? = null

    @Shadow
    private val activeButton = 0

    @Shadow
    private val xpos = 0.0

    @Shadow
    private val ypos = 0.0

    @Inject(
        method = ["onScroll"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDDD)Z",
            ordinal = 0
        )],
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    fun onMouseScrolled(
        handle: Long,
        xOffset: Double,
        yOffset: Double,
        info: CallbackInfo,
        discreteMouseScroll: Boolean,
        mouseWheelSensitivity: Double,
        amountX: Double,
        amountY: Double,
        x: Double,
        y: Double
    ) {
        if (!info.isCancelled) {
            val result = ScreenInputEvent.MOUSE_SCROLLED_PRE.event
                .onScroll(minecraft!!, minecraft.screen!!, x, y, amountX, amountY)
            if (result.isPresent) info.cancel()
        }
    }

    @Inject(
        method = ["onScroll"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDDD)Z",
            ordinal = 0,
            shift = At.Shift.AFTER
        )],
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    fun onMouseScrolledPost(
        handle: Long,
        xOffset: Double,
        yOffset: Double,
        info: CallbackInfo,
        discreteMouseScroll: Boolean,
        mouseWheelSensitivity: Double,
        amountX: Double,
        amountY: Double,
        x: Double,
        y: Double
    ) {
        if (!info.isCancelled) {
            val result = ScreenInputEvent.MOUSE_SCROLLED_POST.event
                .onScroll(minecraft!!, minecraft.screen!!, x, y, amountX, amountY)
        }
    }

    @Inject(
        method = ["onScroll"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z", ordinal = 0)],
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    fun onRawMouseScrolled(
        handle: Long,
        xOffset: Double,
        yOffset: Double,
        info: CallbackInfo,
        discreteMouseScroll: Boolean,
        mouseWheelSensitivity: Double,
        amountX: Double,
        doubleY: Double
    ) {
        if (!info.isCancelled) {
            val result = InputEvent.MOUSE_SCROLL.event.onScroll(minecraft!!, amountX, doubleY)
            if (result.isPresent) info.cancel()
        }
    }

    @Inject(method = ["lambda\$onPress$0", "method_1611"], at = [At("HEAD")], cancellable = true, remap = false)
    private fun onGuiMouseClicked(
        bls: BooleanArray,
        screen: Screen,
        d: Double,
        e: Double,
        button: Int,
        info: CallbackInfo
    ) {
        val minecraft = Minecraft.getInstance()
        if (!info.isCancelled) {
            val result =
                ScreenInputEvent.MOUSE_CLICKED_PRE.event.onClicked(minecraft, screen, d, e, button)
            if (result.isPresent) {
                bls[0] = true
                info.cancel()
            }
        }
    }

    @Inject(method = ["lambda\$onPress$0", "method_1611"], at = [At("RETURN")], cancellable = true, remap = false)
    private fun onGuiMouseClickedPost(
        bls: BooleanArray,
        screen: Screen,
        d: Double,
        e: Double,
        button: Int,
        info: CallbackInfo
    ) {
        val minecraft = Minecraft.getInstance()
        if (!info.isCancelled && !bls[0]) {
            val result =
                ScreenInputEvent.MOUSE_CLICKED_POST.event.onClicked(minecraft, screen, d, e, button)
            if (result.isPresent) {
                bls[0] = true
                info.cancel()
            }
        }
    }

    @Inject(
        method = ["onPress"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;",
            ordinal = 0
        )],
        cancellable = true
    )
    fun onRawMouseClicked(handle: Long, button: Int, action: Int, mods: Int, info: CallbackInfo) {
        if (!info.isCancelled) {
            val result =
                InputEvent.MOUSE_CLICK_PRE.event.onClicked(minecraft!!, button, action, mods)
            if (result.isPresent) info.cancel()
        }
    }

    @Inject(method = ["onPress"], at = [At("RETURN")], cancellable = true)
    fun onRawMouseClickedPost(handle: Long, button: Int, action: Int, mods: Int, info: CallbackInfo) {
        if (handle == minecraft!!.window.window) {
            val result =
                InputEvent.MOUSE_CLICK_POST.event.onClicked(minecraft, button, action, mods)
            if (result.isPresent) info.cancel()
        }
    }

    @Inject(method = ["lambda\$onPress$1", "method_1605"], at = [At("HEAD")], cancellable = true, remap = false)
    private fun onGuiMouseReleased(
        bls: BooleanArray,
        screen: Screen,
        d: Double,
        e: Double,
        button: Int,
        info: CallbackInfo
    ) {
        val minecraft = Minecraft.getInstance()
        if (!info.isCancelled) {
            val result =
                ScreenInputEvent.MOUSE_RELEASED_PRE.event.onRelease(minecraft, screen, d, e, button)
            if (result.isPresent) {
                bls[0] = true
                info.cancel()
            }
        }
    }

    @Inject(method = ["lambda\$onPress$1", "method_1605"], at = [At("RETURN")], cancellable = true, remap = false)
    private fun onGuiMouseReleasedPost(
        bls: BooleanArray,
        screen: Screen,
        d: Double,
        e: Double,
        button: Int,
        info: CallbackInfo
    ) {
        val minecraft = Minecraft.getInstance()
        if (!info.isCancelled && !bls[0]) {
            val result =
                ScreenInputEvent.MOUSE_RELEASED_POST.event.onRelease(minecraft, screen, d, e, button)
            if (result.isPresent) {
                bls[0] = true
                info.cancel()
            }
        }
    }

    @Inject(method = ["method_1602", "lambda\$onMove$11"], at = [At("HEAD")], cancellable = true, remap = false)
    private fun onGuiMouseDraggedPre(
        screen: Screen,
        mouseX: Double,
        mouseY: Double,
        deltaX: Double,
        deltaY: Double,
        ci: CallbackInfo
    ) {
        if (ScreenInputEvent.MOUSE_DRAGGED_PRE.event.onDrag(
                Minecraft.getInstance(), screen, mouseX, mouseY,
                this.activeButton, deltaX, deltaY
            ).isPresent
        ) {
            ci.cancel()
        }
    }

    @Redirect(
        method = ["method_1602", "lambda\$onMove$11"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;mouseDragged(DDIDD)Z",
            remap = true
        ),
        remap = false
    )
    private fun onGuiMouseDraggedPost(
        screen: Screen,
        mouseX: Double,
        mouseY: Double,
        button: Int,
        deltaX: Double,
        deltaY: Double
    ): Boolean {
        if (screen.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            return true
        }
        return ScreenInputEvent.MOUSE_DRAGGED_POST.event
            .onDrag(Minecraft.getInstance(), screen, mouseX, mouseY, button, deltaX, deltaY).isPresent
    }
}