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

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.Difficulty
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.TamableAnimal
import net.minecraft.world.entity.ai.goal.BreakDoorGoal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.DoorBlock
import team._0mods.aeternus.common.init.registry.AeternusRegsitry
import team._0mods.multilib.event.base.common.InteractionEvent
import team._0mods.multilib.event.result.EventResult
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
        val tame = mob
        if (tame is TamableAnimal) {
            if (tame.owner != null) {
                val owner = tame.owner
                var returnValue = false
                if (owner is Player) {
                    InteractionEvent.LEFT_CLICK_BLOCK.register { player, hand, pos, direction ->
                        if (owner == player) {
                            val level = player.level()
                            val block = level.getBlockState(pos).block

                            if (block is DoorBlock) {
                                returnValue = true
                            }
                        }

                        return@register EventResult.pass()
                    }
                    return returnValue
                }
            }
        }

        return if (this.isAltake && this.isNight) super.canUse() else false
    }
}
