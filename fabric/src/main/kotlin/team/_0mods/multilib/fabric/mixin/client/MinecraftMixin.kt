package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.HitResult
import org.objectweb.asm.Opcodes
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.ModifyVariable
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.client.ClientPlayerEvent
import team._0mods.multilib.event.base.client.ScreenEvent
import team._0mods.multilib.event.base.common.InteractionEvent

@Unique
@Mixin(Minecraft::class)
abstract class MinecraftMixin {
    @Shadow
    lateinit var player: LocalPlayer

    @Shadow
    lateinit var hitResult: HitResult

    @Shadow
    abstract fun setScreen(screen: Screen?)

    @Unique
    private val setScreenCancelled = ThreadLocal<Boolean>()

    @Inject(
        method = ["disconnect(Lnet/minecraft/client/gui/screens/Screen;)V"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/client/GameNarrator;clear()V")]
    )
    private fun handleLogin(screen: Screen, ci: CallbackInfo) {
        ClientPlayerEvent.PLAYER_LEAVE.event.onLeave(player)
    }

    @Inject(
        method = ["startUseItem"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 1)],
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private fun rightClickAir(
        ci: CallbackInfo,
        var1: Array<InteractionHand>,
        var2: Int,
        var3: Int,
        interactionHand: InteractionHand,
        itemStack: ItemStack
    ) {
        if (itemStack.isEmpty && (this.hitResult == null || hitResult.type == HitResult.Type.MISS)) {
            InteractionEvent.CLIENT_RIGHT_CLICK_AIR.event.click(player, interactionHand)
        }
    }

    @Inject(
        method = ["startAttack"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;resetAttackStrengthTicker()V",
            ordinal = 0
        )]
    )
    private fun leftClickAir(ci: CallbackInfoReturnable<Boolean>) {
        InteractionEvent.CLIENT_LEFT_CLICK_AIR.event.click(player, InteractionHand.MAIN_HAND)
    }

    @ModifyVariable(
        method = ["setScreen"],
        at = At(
            value = "FIELD",
            opcode = Opcodes.PUTFIELD,
            target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;",
            shift = At.Shift.BY,
            by = -1
        ),
        argsOnly = true
    )
    fun modifyScreen(s: Screen): Screen? {
        var screen: Screen? = s
        val old: Screen? = screen
        val event = ScreenEvent.SET_SCREEN.event.setScreen(screen)
        if (event.isPresent) {
            if (event.isFalse) {
                setScreenCancelled.set(true)
                return old
            } else {
                screen = event.obj
                if (old != null && screen != old) {
                    old.removed()
                }
            }
        }
        setScreenCancelled.set(false)
        return screen
    }

    @Inject(
        method = ["setScreen"],
        at = [At(
            value = "FIELD",
            opcode = Opcodes.PUTFIELD,
            target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;",
            shift = At.Shift.BY,
            by = -1
        )],
        cancellable = true
    )
    fun cancelSetScreen(screen: Screen?, ci: CallbackInfo) {
        if (setScreenCancelled.get()) {
            ci.cancel()
            setScreenCancelled.set(false)
        }
    }
}