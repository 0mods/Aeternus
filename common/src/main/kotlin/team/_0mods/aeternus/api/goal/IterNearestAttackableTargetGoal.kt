/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.goal

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import team._0mods.aeternus.common.init.registry.AeternusRegsitry
import java.util.function.Predicate

class IterNearestAttackableTargetGoal<T: LivingEntity>(
    mob: Mob,
    clazz: Class<T>,
    interval: Int,
    mustSee: Boolean,
    mustReach: Boolean,
    condition: Predicate<LivingEntity>?
): NearestAttackableTargetGoal<T>(mob, clazz, interval, mustSee, mustReach, condition) {
    private val isAlTake = this.mob.level().dimensionTypeId() == AeternusRegsitry.iterDimType
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
