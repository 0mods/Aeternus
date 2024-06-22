/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.platformredirect.api.block.magic

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.service.ResearchHelper

abstract class CursedBlockCondition {
    companion object {
        @JvmStatic
        fun LivingEntity.equalsEffect(effect: MobEffect): CursedBlockCondition = EqualEffect(effect, this)

        @JvmStatic
        fun Player.equalsResearch(research: Research): CursedBlockCondition = EqualResearch(this, research)
    }

    abstract fun isSuccess(): Boolean

    class EqualEffect internal constructor(private val effect: MobEffect, private val livingEntity: LivingEntity): CursedBlockCondition() {
        override fun isSuccess(): Boolean = livingEntity.activeEffects.equals(effect)
    }

    class EqualResearch internal constructor(private val player: Player, private val research: Research): CursedBlockCondition() {
        override fun isSuccess(): Boolean = ResearchHelper.hasResearch(player, research)
    }
}
