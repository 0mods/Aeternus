package team._0mods.multilib.fabric.mixin.client

import com.mojang.blaze3d.platform.Window
import com.mojang.blaze3d.shaders.Program
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.server.packs.resources.ResourceProvider
import org.joml.Matrix4f
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.client.ScreenEvent
import team._0mods.multilib.event.base.client.ShaderReloadEvent
import java.util.function.Consumer

@Mixin(value = [GameRenderer::class], priority = 1100)
class GameRendererMixin {
    @Shadow
    @Final
    private var minecraft: Minecraft? = null

    @Inject(
        method = ["render(FJZ)V"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltip(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            ordinal = 0
        )],
        locals = LocalCapture.CAPTURE_FAILEXCEPTION,
        cancellable = true
    )
    fun renderScreenPre(
        tickDelta: Float,
        startTime: Long,
        tick: Boolean,
        ci: CallbackInfo,
        speedAppliedTickDelta: Float,
        isGameLoadFinished: Boolean,
        mouseX: Int,
        mouseY: Int,
        window: Window,
        matrix: Matrix4f,
        matrices: PoseStack,
        graphics: GuiGraphics
    ) {
        if (ScreenEvent.RENDER_PRE.event
                .render(minecraft!!.screen!!, graphics, mouseX, mouseY, minecraft!!.deltaFrameTime).isFalse
        ) {
            ci.cancel()
        }
    }

    @Inject(
        method = ["render(FJZ)V"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltip(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            shift = At.Shift.AFTER,
            ordinal = 0
        )],
        locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    fun renderScreenPost(
        tickDelta: Float,
        startTime: Long,
        tick: Boolean,
        ci: CallbackInfo,
        speedAppliedTickDelta: Float,
        isGameLoadFinished: Boolean,
        mouseX: Int,
        mouseY: Int,
        window: Window,
        matrix: Matrix4f,
        matrices: PoseStack,
        graphics: GuiGraphics
    ) {
        ScreenEvent.RENDER_POST.event
            .render(minecraft!!.screen!!, graphics, mouseX, mouseY, minecraft!!.deltaFrameTime)
    }

    @Inject(
        method = ["reloadShaders"],
        at = [At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0)],
        locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    fun reloadShaders(
        provider: ResourceProvider,
        ci: CallbackInfo,
        programs: List<Program>,
        shaders: MutableList<Pair<ShaderInstance, Consumer<ShaderInstance>>>
    ) {
        ShaderReloadEvent.EVENT.event.reload(provider) { shader, callback ->
            shaders.add(shader to callback)
        }
    }
}