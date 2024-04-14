package team._0mods.multilib.fabric.mixin

import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(Player::class)
class PlayerAttackMixin {
    @Inject(method = ["hurt"], at = [At("HEAD")], cancellable = true)
    private fun hurt(damageSource: DamageSource, f: Float, cir: CallbackInfoReturnable<Boolean>) {
        val obj = this as Any
        if (EntityEvent.HURT.event.hurt(this as LivingEntity, damageSource, f).isFalse && obj is Player) {
            cir.setReturnValue(false)
        }
    }
}
