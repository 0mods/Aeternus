package team._0mods.multilib.fabric.mixin

import net.minecraft.core.particles.ParticleOptions
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.ExplosionDamageCalculator
import net.minecraft.world.level.Level
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.ExplosionEvent

@Mixin(Level::class)
class LevelMixin {
    @Inject(
        method = ["explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level\$ExplosionInteraction;ZLnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/sounds/SoundEvent;)Lnet/minecraft/world/level/Explosion;"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/world/level/Explosion;explode()V")],
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private fun explodePre(
        entity: Entity,
        damageSource: DamageSource,
        explosionDamageCalculator: ExplosionDamageCalculator,
        d: Double,
        e: Double,
        f: Double,
        g: Float,
        bl: Boolean,
        explosionInteraction: Level.ExplosionInteraction,
        bl2: Boolean,
        particleOptions: ParticleOptions,
        particleOptions2: ParticleOptions,
        soundEvent: SoundEvent,
        cir: CallbackInfoReturnable<Explosion>,
        blockInteraction: Explosion.BlockInteraction,
        explosion: Explosion
    ) {
        val obj = this as Any
        if (ExplosionEvent.PRE.event.explode(obj as Level, explosion).isFalse) {
            cir.setReturnValue(explosion)
        }
    }
}