/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.mixin.entity

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
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.aeternus.api.goal.AltakeAttackGoal
import team._0mods.aeternus.api.goal.AltakeBreakDoorGoal
import team._0mods.aeternus.api.goal.AltakeNearestAttackableTargetGoal
import team._0mods.aeternus.common.init.registry.AeternusRegsitry
import java.util.function.Predicate

@Mixin(Animal::class, priority = 0 /*high priority*/)
abstract class AnimalMixin(
    type: EntityType<out AgeableMob>,
    level: Level
) : AgeableMob(type, level), Enemy {
    private val difficultyPredicate: Predicate<Difficulty> = Predicate {
        return@Predicate (
                this.level().isNight && this.level().dimensionTypeId() == AeternusRegsitry.alTakeDim
        ) && (it == Difficulty.EASY || it == Difficulty.NORMAL || it == Difficulty.HARD)
    }

    @Inject(method = ["<init>"], at = [At("TAIL")])
    fun initInj(type: EntityType<out Animal>, level: Level?, ci: CallbackInfo) {
        if (level != null && !level.isClientSide) {
            if (type != EntityType.PANDA) {
                aggresiableGoals()
            }
        }
    }

    @Unique
    private fun aggresiableGoals() {
        addGoal(1, AltakeBreakDoorGoal(this, difficultyPredicate))
        addGoal(2, AltakeAttackGoal(this, 1.0, true))
        addGoal(2, AltakeNearestAttackableTargetGoal(this, Player::class.java, false))
    }

    @Unique
    private fun addGoal(priority: Int, goal: Goal) {
        this.goalSelector.addGoal(priority, goal)
    }
}
