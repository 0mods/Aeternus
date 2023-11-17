package team.zeds.aeternus.api.item

import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.HoeItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.PickaxeItem
import net.minecraft.world.item.ShovelItem
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tier
import net.minecraft.world.item.Tiers

class Materials private constructor(
    private val modId: String,
    private val materialName: String,
    private val mainItem: Item?,
    private val pickaxeItem: PickaxeItem?,
    private val swordItem: SwordItem?,
    private val shovelItem: ShovelItem?,
    private val axeItem: AxeItem?,
    private val hoeItem: HoeItem?,
    private val helmet: ArmorItem?,
    private val chest: ArmorItem?,
    private val legs: ArmorItem?,
    private val feet: ArmorItem?
) {
    fun getParentItem(): Item = mainItem ?: thrower("parent item")

    fun getPickaxe(): PickaxeItem = pickaxeItem ?: thrower("pickaxe")

    fun getSword(): SwordItem = swordItem ?: thrower("sword")

    fun getShovel(): ShovelItem = shovelItem ?: thrower("shovel")

    fun getAxe(): AxeItem = axeItem ?: thrower("axe")

    fun getHoe(): HoeItem = hoeItem ?: thrower("hoe")

    fun getHelmet(): ArmorItem = helmet ?: thrower("helmet")

    fun getChestplate(): ArmorItem = chest ?: thrower("chest")

    fun getLeggings(): ArmorItem = legs ?: thrower("leggings")

    fun getBoots(): ArmorItem = feet ?: thrower("boots")

    private fun <T> thrower(string: String): T = throw UnsupportedOperationException("Enable to get \"${string.uppercase()}\" for material \"$modId:$materialName\", if it is null!")

    companion object {
        @JvmStatic
        fun create(modId: String): MaterialCreator = MaterialCreator(modId)
    }

    class Builder(private val materialId: String, private val modId: String) {
        fun setupProperties(properties: MaterialProperties.() -> Unit) {}

        class MaterialProperties {
            var type: Tier = Tiers.IRON
            var armorType: ArmorMaterial = ArmorMaterials.IRON
            var durabilityModifier = 0F
            var usageModifier = 0F
        }
    }

    class MaterialCreator(val modId: String) {
        fun builder(materialId: String) = Builder(materialId, modId)
    }
}