package team.zeds.aeternus.mixin

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.NeutralMob
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.Level
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team.zeds.aeternus.init.registry.AeternusRegsitry
import java.util.*

@Mixin(NeutralMob::class, priority = 1)
abstract class NeutralMobMixin {
    @Shadow
    abstract fun isAngry(): Boolean

    @Shadow
    abstract fun getRemainingPersistentAngerTime(): Int

    @Shadow
    abstract fun getPersistentAngerTarget(): UUID?

    @Inject(method = ["isAngry"], at = [At("RETURN")], cancellable = true)
    fun isAngryInj(cir: CallbackInfoReturnable<Boolean>) {
        val mob = this as NeutralMob
        if (mob is Mob) {
            if (mob.level().isNight && mob.level()
                    .dimensionTypeId() === AeternusRegsitry.altakeDim
            ) cir.setReturnValue(true) else cir.setReturnValue(
                getRemainingPersistentAngerTime() > 0
            )
        } else cir.setReturnValue(false)
    }

    @Inject(method = ["isAngryAtAllPlayers"], at = [At("RETURN")], cancellable = true)
    fun isAngryAtAllPlayersInj(level: Level, cir: CallbackInfoReturnable<Boolean>) {
        cir.setReturnValue(
            level.isNight && level.dimensionTypeId() === AeternusRegsitry.altakeDim || level.gameRules.getBoolean(
                GameRules.RULE_UNIVERSAL_ANGER
            ) && isAngry() && getPersistentAngerTarget() == null
        )
    }
}