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

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Tier
import net.minecraft.world.item.component.Tool
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block
import team._0mods.aeternus.api.util.rl

class ToolMaterialCreation private constructor(
    private val maxUses: Int,
    private val attackSpeed: Float,
    private val attackBonus: Float,
    private val incorrectBlocks: TagKey<Block>,
    private val enchant: Int,
    private val ingredient: Ingredient,
    private val toolProps: (TagKey<Block>) -> Tool
): Tier {
    override fun getUses(): Int = this.maxUses

    override fun getSpeed(): Float = this.attackSpeed

    override fun getAttackDamageBonus(): Float = this.attackBonus

    override fun getIncorrectBlocksForDrops(): TagKey<Block> = this.incorrectBlocks

    override fun getEnchantmentValue(): Int = this.enchant

    override fun getRepairIngredient(): Ingredient = this.ingredient

    override fun createToolProperties(block: TagKey<Block>): Tool = toolProps(block)

    companion object {
        internal val builder = Builder()
    }

    class Builder {
        private var maxUses: Int = 250
        private var speed: Float = 6f
        private var attackBonus: Float = 2.0f
        private var incorrectBlocks: TagKey<Block> = TagKey.create(Registries.BLOCK, "air".rl)
        private var enchant: Int = 14
        private var toolProps: (TagKey<Block>) -> Tool = {
            Tool(listOf(Tool.Rule.deniesDrops(incorrectBlocks), Tool.Rule.minesAndDrops(it, speed)), speed, attackBonus.toInt())
        }
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

        fun incorrectBlocks(tag: String): Builder {
            this.incorrectBlocks = TagKey.create(Registries.BLOCK, tag.rl)
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

        fun toolProperties(it: (TagKey<Block>) -> Tool): Builder {
            this.toolProps = it
            return this
        }

        fun build(): Tier = ToolMaterialCreation(maxUses, speed, attackBonus, incorrectBlocks, enchant, repair, toolProps)
    }
}
