package team.zeds.aeternus.api.goal

import net.minecraft.world.Difficulty
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.BreakDoorGoal
import team.zeds.aeternus.init.registry.AeternusRegsitry
import java.util.function.Predicate

class AltakeBreakDoorGoal(
    mob: Mob,
    breakTime: Int,
    validDifficulties: Predicate<Difficulty>
): BreakDoorGoal(mob, breakTime, validDifficulties) {
    private val isAltake = this.mob.level().dimensionTypeId() == AeternusRegsitry.altakeDim
    private val isNight = this.mob.level().isNight

    constructor(mob: Mob, validDifficulties: Predicate<Difficulty>):
            this(mob, -1, validDifficulties)

    override fun canUse(): Boolean {
        return if (this.isAltake && this.isNight) super.canUse() else true
    }
}