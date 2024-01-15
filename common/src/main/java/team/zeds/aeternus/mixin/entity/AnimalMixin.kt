package team.zeds.aeternus.mixin.entity

import net.minecraft.world.Difficulty
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.monster.Enemy
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import team.zeds.aeternus.api.goal.AltakeAttackGoal
import team.zeds.aeternus.api.goal.AltakeBreakDoorGoal
import team.zeds.aeternus.api.goal.AltakeNearestAttackableTargetGoal
import team.zeds.aeternus.init.registry.AeternusRegsitry
import java.util.function.Predicate

@Mixin(Animal::class, priority = 0 /*high priority*/)
abstract class AnimalMixin(
    type: EntityType<out AgeableMob>,
    level: Level
) : AgeableMob(type, level), Enemy {
    private val difficultyPredicate: Predicate<Difficulty> = Predicate {
        return@Predicate (
                this.level().isNight && this.level().dimensionTypeId() == AeternusRegsitry.altakeDim
        ) && (it == Difficulty.EASY || it == Difficulty.NORMAL || it == Difficulty.HARD)
    }

    override fun registerGoals() {
        addGoal(1, AltakeBreakDoorGoal(this, difficultyPredicate))
        addGoal(2, AltakeAttackGoal(this, 1.0, true))
        addGoal(2, AltakeNearestAttackableTargetGoal(this, Player::class.java, false))
    }

    @Unique
    private fun addGoal(priority: Int, goal: Goal) {
        this.goalSelector.addGoal(priority, goal)
    }
}