package team._0mods.aeternus.api.goal

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import team._0mods.aeternus.init.registry.AeternusRegsitry
import java.util.function.Predicate

class AltakeNearestAttackableTargetGoal<T: LivingEntity>(
    mob: Mob,
    clazz: Class<T>,
    interval: Int,
    mustSee: Boolean,
    mustReach: Boolean,
    condition: Predicate<LivingEntity>?
): NearestAttackableTargetGoal<T>(mob, clazz, interval, mustSee, mustReach, condition) {
    private val isAlTake = this.mob.level().dimensionTypeId() == AeternusRegsitry.alTakeDim
    private val isNight = mob.level().isNight

    constructor(mob: Mob, clazz: Class<T>, mustSee: Boolean, mustReach: Boolean):
            this(mob, clazz, 10, mustSee, mustReach, null)

    constructor(mob: Mob, clazz: Class<T>, mustSee: Boolean, condition: Predicate<LivingEntity>?):
            this(mob, clazz, 10, mustSee, false, condition)

    constructor(mob: Mob, clazz: Class<T>, mustSee: Boolean):
            this(mob, clazz, 10, mustSee, false, null)

    override fun canUse(): Boolean {
        return if (this.isNight && this.isAlTake) super.canUse() else false
    }
}