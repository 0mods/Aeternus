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

import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

class AltakeAttackGoal(mob: PathfinderMob, speedMod: Double, followIfNotSeen: Boolean): MeleeAttackGoal(
    mob,
    speedMod,
    followIfNotSeen
) {
    private val isAltake = this.mob.level().dimensionTypeId() == AeternusRegsitry.alTakeDim
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
