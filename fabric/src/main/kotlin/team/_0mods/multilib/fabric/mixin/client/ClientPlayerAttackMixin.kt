package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.player.RemotePlayer
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(value = [LocalPlayer::class, RemotePlayer::class])
class ClientPlayerAttackMixin {
    @Inject(method = ["hurt"], at = [At("HEAD")], cancellable = true)
    fun hurt(source: DamageSource, f: Float, cir: CallbackInfoReturnable<Boolean>) {
        if (EntityEvent.HURT.event.hurt(((this as Any) as LivingEntity), source, f).isFalse && (this as Any) is Player) {
            cir.returnValue = false
        }
    }
}