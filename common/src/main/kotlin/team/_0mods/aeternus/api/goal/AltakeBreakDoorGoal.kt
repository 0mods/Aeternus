/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.goal

import net.minecraft.world.Difficulty
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.BreakDoorGoal
import team._0mods.aeternus.common.init.registry.AeternusRegsitry
import java.util.function.Predicate

class AltakeBreakDoorGoal(
    mob: Mob,
    breakTime: Int,
    validDifficulties: Predicate<Difficulty>
): BreakDoorGoal(mob, breakTime, validDifficulties) {
    private val isAltake = this.mob.level().dimensionTypeId() == AeternusRegsitry.alTakeDim
    private val isNight = this.mob.level().isNight

    constructor(mob: Mob, validDifficulties: Predicate<Difficulty>):
            this(mob, -1, validDifficulties)

    override fun canUse(): Boolean {
        return if (this.isAltake && this.isNight) super.canUse() else true
    }
}
