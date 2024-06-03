/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.mixin.entity

import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.monster.Enemy
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.aeternus.api.goal.IterAttackGoal
import team._0mods.aeternus.api.goal.IterBreakDoorGoal
import team._0mods.aeternus.api.goal.IterNearestAttackableTargetGoal
import team._0mods.aeternus.api.util.diff

// No needed refmap
@Mixin(value = [Animal::class], priority = 0 /*high priority*/)
abstract class AnimalMixin protected constructor(
    entityType: EntityType<out Animal>,
    level: Level
) : AgeableMob(entityType, level), Enemy {
    @Inject(method = ["<init>"], at = [At("TAIL")])
    private fun initInj(entityType: EntityType<out Animal>, level: Level?, ci: CallbackInfo) {
        if (level != null && !level.isClientSide) `aeternus$agreGoals`()
    }

    @Unique
    private fun `aeternus$agreGoals`() {
        goalSelector.addGoal(2, IterAttackGoal((this as Animal), 1.0, true))
        goalSelector.addGoal(
            2, IterNearestAttackableTargetGoal(
                (this as Animal), Player::class.java, false
            )
        )
        goalSelector.addGoal(1, IterBreakDoorGoal((this as Animal), diff(level())))
    }
}
