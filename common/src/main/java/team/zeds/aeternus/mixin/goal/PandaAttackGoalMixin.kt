package team.zeds.aeternus.mixin.goal

import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.animal.Panda
import net.minecraft.world.entity.animal.Panda.PandaAttackGoal
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team.zeds.aeternus.init.registry.AeternusRegsitry

@Mixin(PandaAttackGoal::class)
abstract class PandaAttackGoalMixin(mob: PathfinderMob, speedMod: Double, followIfNotSeen: Boolean) : MeleeAttackGoal(mob, speedMod,
    followIfNotSeen
) {
    @Inject(method = ["canUse"], at = [At("RETURN")], cancellable = true)
    fun canUseInj(cir: CallbackInfoReturnable<Boolean>) {
        if (mob.level().isNight && mob.level().dimensionTypeId() == AeternusRegsitry.altakeDim)
            cir.returnValue = true
        else cir.returnValue = (this.mob as Panda).canPerformAction() && super.canUse()
    }
}