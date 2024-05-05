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

import net.minecraft.Util
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import team._0mods.aeternus.api.util.rl
import java.util.*

class ArmorMaterialCreation(
    private val name: String,
    private val durMultiper: Int,
    private val helmetDef: Int,
    private val chestDef: Int,
    private val legsDef: Int,
    private val bootsDef: Int,
    private val enchValue: Int,
    private val equipSound: () -> SoundEvent,
    private val repairIngr: () -> Ingredient,
    private val toughness: Float,
    private val kbResistance: Float
): ArmorMaterial {
    companion object {
        @JvmStatic
        fun Companion.builder(id: String) = this.builder(id.rl)

        @JvmStatic
        fun Companion.builder(id: ResourceLocation) = Builder(id)
    }

    private val hfft: EnumMap<ArmorItem.Type, Int> = Util.make(
        EnumMap<ArmorItem.Type, Int>(ArmorItem.Type::class.java)
    ) {
        it[ArmorItem.Type.HELMET] = 11
        it[ArmorItem.Type.CHESTPLATE] = 16
        it[ArmorItem.Type.LEGGINGS] = 15
        it[ArmorItem.Type.BOOTS] = 13
    }

    private val pfft = Util.make(EnumMap<ArmorItem.Type, Int>(ArmorItem.Type::class.java)) {
        it[ArmorItem.Type.HELMET] = helmetDef
        it[ArmorItem.Type.CHESTPLATE] = chestDef
        it[ArmorItem.Type.LEGGINGS] = legsDef
        it[ArmorItem.Type.BOOTS] = bootsDef
    }

    override fun getDurabilityForType(type: ArmorItem.Type): Int = hfft[type]!! * durMultiper

    override fun getDefenseForType(type: ArmorItem.Type): Int = pfft[type]!!

    override fun getEnchantmentValue(): Int = enchValue

    override fun getEquipSound(): SoundEvent = equipSound()

    override fun getRepairIngredient(): Ingredient = repairIngr()

    override fun getName(): String = name

    override fun getToughness(): Float = toughness

    override fun getKnockbackResistance(): Float = kbResistance
    
    class Builder(private val id: ResourceLocation) {
        private var durMod: Int = 9

        private var helmetDef = 2
        private var chestDef = 6
        private var legsDef = 5
        private var bootsDef = 2

        private var enchValue = 9

        private var equipSound = { SoundEvents.ARMOR_EQUIP_IRON }
        private var repairIngr = { Ingredient.of(Items.IRON_INGOT) }

        private var toughness: Float = 0F
        private var kbResistance: Float = 0F

        fun helmetDef(def: Int): Builder {
            helmetDef = def
            return this
        }

        fun chestDef(def: Int): Builder {
            chestDef = def
            return this
        }

        fun legsDef(def: Int): Builder {
            legsDef = def
            return this
        }

        fun bootsDef(def: Int): Builder {
            bootsDef = def
            return this
        }

        fun fullDef(helmet: Int, chest: Int, legs: Int, boots: Int) =
            this.helmetDef(helmet)
            .chestDef(chest)
            .legsDef(legs)
            .bootsDef(boots)

        fun equipSound(sound: () -> SoundEvent): Builder {
            equipSound = sound
            return this
        }

        fun ingredient(ingredient: () -> Ingredient): Builder {
            repairIngr = ingredient
            return this
        }

        fun ingredient(vararg ingredients: ItemLike): Builder = ingredient { Ingredient.of(*ingredients) }

        fun ingredient(vararg ingredients: ItemStack): Builder = ingredient { Ingredient.of(*ingredients) }

        fun ingredient(ingredients: TagKey<Item>): Builder = ingredient { Ingredient.of(ingredients) }

        fun toughness(tough: Float): Builder {
            toughness = tough
            return this
        }

        fun knockback(kb: Float): Builder {
            kbResistance = kb
            return this
        }

        fun durability(dur: Int): Builder {
            durMod = dur
            return this
        }

        @get:JvmName("build")
        val build: ArmorMaterialCreation
            get() = ArmorMaterialCreation(id.toString(), durMod, helmetDef, chestDef, legsDef, bootsDef, enchValue, equipSound, repairIngr, toughness, kbResistance)
    }
}