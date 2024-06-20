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
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import team._0mods.aeternus.api.util.rl
import java.util.*

class ArmorMaterialCreation(private val id: ResourceLocation) {
    private var durMod = 9

    private var dyable = false
    private val overlays: MutableList<ArmorMaterial.Layer> = mutableListOf()

    private var helmetDef = 2
    private var chestDef = 6
    private var legsDef = 5
    private var bootsDef = 2
    private var bodyDef = legsDef

    private var enchValue = 9

    private var equipSound = { SoundEvents.ARMOR_EQUIP_IRON }
    private var repairIngr = { Ingredient.of(Items.IRON_INGOT) }

    private var toughness: Float = 0F
    private var kbResistance: Float = 0F

    companion object {
        @JvmStatic
        fun Companion.builder(id: String) = this.builder(id.rl)

        @JvmStatic
        fun Companion.builder(id: ResourceLocation) = ArmorMaterialCreation(id)
    }

    private val pfft = Util.make(EnumMap<ArmorItem.Type, Int>(ArmorItem.Type::class.java)) {
        it[ArmorItem.Type.HELMET] = helmetDef
        it[ArmorItem.Type.CHESTPLATE] = chestDef
        it[ArmorItem.Type.LEGGINGS] = legsDef
        it[ArmorItem.Type.BOOTS] = bootsDef
        it[ArmorItem.Type.BODY] = bodyDef
    }

    fun helmetDef(def: Int): ArmorMaterialCreation {
        helmetDef = def
        return this
    }

    fun chestDef(def: Int): ArmorMaterialCreation {
        chestDef = def
        return this
    }

    fun legsDef(def: Int): ArmorMaterialCreation {
        legsDef = def
        return this
    }

    fun bootsDef(def: Int): ArmorMaterialCreation {
        bootsDef = def
        return this
    }

    fun bodyDef(def: Int): ArmorMaterialCreation {
        bodyDef = def
        return this
    }

    fun fullDef(helmet: Int, chest: Int, legs: Int, boots: Int, body: Int = legs) =
        this.helmetDef(helmet)
            .chestDef(chest)
            .legsDef(legs)
            .bootsDef(boots)
            .bodyDef(body)

    fun equipSound(sound: () -> Holder<SoundEvent>): ArmorMaterialCreation {
        equipSound = sound
        return this
    }

    fun ingredient(ingredient: () -> Ingredient): ArmorMaterialCreation {
        repairIngr = ingredient
        return this
    }

    fun ingredient(vararg ingredients: ItemLike): ArmorMaterialCreation = ingredient { Ingredient.of(*ingredients) }

    fun ingredient(vararg ingredients: ItemStack): ArmorMaterialCreation = ingredient { Ingredient.of(*ingredients) }

    fun ingredient(ingredients: TagKey<Item>): ArmorMaterialCreation = ingredient { Ingredient.of(ingredients) }

    fun toughness(tough: Float): ArmorMaterialCreation {
        toughness = tough
        return this
    }

    fun knockback(kb: Float): ArmorMaterialCreation {
        kbResistance = kb
        return this
    }

    fun durability(dur: Int): ArmorMaterialCreation {
        durMod = dur
        return this
    }

    fun dyableMainLayer(): ArmorMaterialCreation {
        dyable = true
        return this
    }

    fun overlay(vararg overlays: ArmorMaterial.Layer): ArmorMaterialCreation {
        this.overlays.addAll(overlays)
        return this
    }

    @get:JvmName("build")
    val build: Holder<ArmorMaterial>
        get() {
            val layers = mutableListOf<ArmorMaterial.Layer>()
            layers.add(ArmorMaterial.Layer(id, "", dyable))
            if (overlays.isNotEmpty()) {
                layers.addAll(overlays)
                overlays.clear()
            }

            return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                id,
                ArmorMaterial(
                    pfft,
                    enchValue,
                    equipSound(),
                    repairIngr,
                    layers,
                    toughness,
                    kbResistance
                )
            )
        }
}