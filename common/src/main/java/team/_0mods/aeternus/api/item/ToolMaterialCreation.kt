/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.item

import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient

class ToolMaterialCreation private constructor(
    private val maxUses: Int,
    private val attackSpeed: Float,
    private val attackBonus: Float,
    private val harvestLevel: Int,
    private val enchant: Int,
    private val ingredient: Ingredient
): Tier {
    override fun getUses(): Int = this.maxUses

    override fun getSpeed(): Float = this.attackSpeed

    override fun getAttackDamageBonus(): Float = this.attackBonus

    override fun getLevel(): Int = this.harvestLevel

    override fun getEnchantmentValue(): Int = this.enchant

    override fun getRepairIngredient(): Ingredient = this.ingredient

    companion object {
        internal val builder = Builder()
    }

    class Builder {
        private var maxUses: Int = 250
        private var speed: Float = 6f
        private var attackBonus: Float = 2.0f
        private var harvestLevel: Int = 2
        private var enchant: Int = 14
        private var repair: Ingredient = Ingredient.EMPTY

        fun maxUses(uses: Int): Builder {
            this.maxUses = uses
            return this
        }

        fun speed(it: Float): Builder {
            this.speed = it
            return this
        }

        fun attackBonus(it: Float): Builder {
            this.attackBonus = it
            return this
        }

        fun harvestLevel(it: Int): Builder {
            this.harvestLevel = it
            return this
        }

        fun enchant(it: Int): Builder {
            this.enchant = it
            return this
        }

        fun repair(it: Ingredient): Builder {
            this.repair = it
            return this
        }

        fun build(): Tier = ToolMaterialCreation(maxUses, speed, attackBonus, harvestLevel, enchant, repair)
    }
}
