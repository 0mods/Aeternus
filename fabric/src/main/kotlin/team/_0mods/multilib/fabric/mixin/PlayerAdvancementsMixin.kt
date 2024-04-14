package team._0mods.multilib.fabric.mixin

import net.minecraft.advancements.AdvancementHolder
import net.minecraft.server.PlayerAdvancements
import net.minecraft.server.level.ServerPlayer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.PlayerEvent

@Mixin(PlayerAdvancements::class)
class PlayerAdvancementsMixin {
    @Shadow
    private val player: ServerPlayer? = null

    @Inject(
        method = ["award"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/advancements/AdvancementRewards;grant(Lnet/minecraft/server/level/ServerPlayer;)V",
            shift = At.Shift.AFTER
        )]
    )
    private fun award(advancement: AdvancementHolder, string: String, cir: CallbackInfoReturnable<Boolean>) {
        PlayerEvent.PLAYER_ADVANCEMENT.event.award(player!!, advancement)
    }
}
