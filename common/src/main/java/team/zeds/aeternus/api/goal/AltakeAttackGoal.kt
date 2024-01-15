package team.zeds.aeternus.api.goal

import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import team.zeds.aeternus.init.registry.AeternusRegsitry

class AltakeAttackGoal(mob: PathfinderMob, speedMod: Double, followIfNotSeen: Boolean): MeleeAttackGoal(
    mob,
    speedMod,
    followIfNotSeen
) {
    private val isAltake = this.mob.level().dimensionTypeId() == AeternusRegsitry.altakeDim
    private val isNight = this.mob.level().isNight
    private var raiseArmTick: Int = 0

    override fun start() {
        if (this.isNight && this.isAltake) {
            super.start()
            this.raiseArmTick = 0
        }
    }

    override fun stop() {
        if (!this.isNight && this.isAltake) {
            super.stop()
            this.mob.isAggressive = false
        }
    }

    override fun tick() {
        if (this.isNight && this.isAltake) {
            super.tick()
            ++this.raiseArmTick
            this.mob.isAggressive = this.raiseArmTick >= 3 && this.ticksUntilNextAttack < this.attackInterval / 4
        }
    }
}