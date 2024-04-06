package team._0mods.multilib.fabric.mixin

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(value = [LivingEntity::class, Player::class, ServerPlayer::class])
class LivingDeath {
    @Inject(method = ["die"], at = [At("HEAD")], cancellable = true)
    private fun die(source: DamageSource, ci: CallbackInfo) {
        val entObj = this as Any
        if (EntityEvent.LIVING_DEATH.event.die(entObj as LivingEntity, source).isFalse) {
            ci.cancel()
        }
    }
}