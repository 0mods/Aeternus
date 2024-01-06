package team.zeds.aeternus.api.item

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.HoeItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.PickaxeItem
import net.minecraft.world.item.ShovelItem
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tier
import net.minecraft.world.item.Tiers
import team.zeds.aeternus.provider.ServiceProvider
import team.zeds.aeternus.init.resloc

internal class Materials private constructor(
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

    private fun <T> thrower(string: String): T =
        throw UnsupportedOperationException("Enable to get \"${string.uppercase()}\" for material \"$modId:$materialName\", if it is null!")

    companion object {
        @Deprecated("Removed in next time.",
            ReplaceWith("MaterialCreator(modId)", "team.zeds.aeternus.api.item.Materials.MaterialCreator")
        )

        @JvmStatic
        fun create(modId: String, materialId: String) = PreparingBuilder(ResourceLocation(modId, materialId))

        @JvmStatic
        fun create(res: ResourceLocation) = PreparingBuilder(res)
    }

    class PreparingBuilder(res: ResourceLocation) {
        private val props: MaterialProperties = MaterialProperties(res.path, res.namespace)

        fun setupProperties(properties: MaterialProperties.() -> Unit): Builder {
            props.apply(properties)
            return Builder(props)
        }
    }

    class MaterialProperties(val modId: String, val materialId: String) {
        var tier: Tier = Tiers.IRON
        var armorTier: ArmorMaterial = ArmorMaterials.IRON
        var damage = 0F
        var attackSpeed = 0F
        var mainTab: CreativeModeTab? = null
        var primaryTabFor: Map<String, CreativeModeTab> = mutableMapOf()
    }

    class Builder(private val props: MaterialProperties) {
        private var mainItem: Item? = null
        private var pickaxeItem: PickaxeItem? = null
        private var swordItem: SwordItem? = null
        private var shovelItem: ShovelItem? = null
        private var axeItem: AxeItem? = null
        private var hoeItem: HoeItem? = null
        private var helmet: ArmorItem? = null
        private var chest: ArmorItem? = null
        private var legs: ArmorItem? = null
        private var feet: ArmorItem? = null

        fun mainItem(itemProps: ()-> Properties = ::Properties): Builder {
            val item = Item(itemProps.invoke())

            tabSetup(item)

            this.mainItem = item

            return this
        }

        fun pickaxe(itemProps: ()-> Properties = ::Properties): Builder {
            val item = PickaxeItemImpl(props.tier, props.damage, props.attackSpeed, itemProps.invoke())

            tabSetup(item, "pickaxe")

            this.pickaxeItem = item

            return this
        }

        fun sword(itemProps: ()-> Properties = ::Properties): Builder {
            val item = SwordItemImpl(props.tier, props.damage, props.attackSpeed, itemProps.invoke())

            tabSetup(item, "sword")

            this.swordItem = item

            return this
        }

        fun shovel(itemProps: ()-> Properties = ::Properties): Builder {
            val item = ShovelItemImpl(props.tier, props.damage, props.attackSpeed, itemProps.invoke())

            tabSetup(item, "shovel")

            this.shovelItem = item

            return this
        }

        fun axe(itemProps: ()-> Properties = ::Properties): Builder {
            val item = AxeItemImpl(props.tier, props.damage, props.attackSpeed, itemProps.invoke())

            tabSetup(item, "axe")

            this.axeItem = item

            return this
        }

        fun hoe(itemProps: ()-> Properties = ::Properties): Builder {
            val item = HoeItemImpl(props.tier, props.damage, props.attackSpeed, itemProps.invoke())

            tabSetup(item, "hoe")

            this.hoeItem = item

            return this
        }

        fun helmet(itemProps: ()-> Properties = ::Properties): Builder {
            val item = ArmorItem(this.props.armorTier, ArmorItem.Type.HELMET, itemProps.invoke())

            tabSetup(item, "helmet")

            this.helmet = item

            return this
        }

        fun chest(itemProps: ()-> Properties = ::Properties): Builder {
            val item = ArmorItem(this.props.armorTier, ArmorItem.Type.CHESTPLATE, itemProps.invoke())

            tabSetup(item, "chest")

            this.chest = item

            return this
        }

        fun legs(itemProps: ()-> Properties = ::Properties): Builder {
            val item = ArmorItem(this.props.armorTier, ArmorItem.Type.LEGGINGS, itemProps.invoke())

            tabSetup(item, "legs")

            this.legs = item

            return this
        }

        fun boots(itemProps: ()-> Properties = ::Properties): Builder {
            val item = ArmorItem(this.props.armorTier, ArmorItem.Type.BOOTS, itemProps.invoke())

            tabSetup(item, "boots")

            this.feet = item

            return this
        }

        fun fullArmor(itemProps: ()-> Properties = ::Properties) = this.helmet(itemProps).chest(itemProps).legs(itemProps).boots(itemProps)

        fun fullTools(itemProps: () -> Properties = ::Properties) = this.pickaxe(itemProps).axe(itemProps).sword(itemProps).hoe(itemProps).shovel(itemProps)

        fun fullSet(itemProps: () -> Properties = ::Properties) = this.fullArmor(itemProps).fullTools(itemProps).mainItem(itemProps)

        fun build(): Materials {
            // register all before creating
            if (mainItem != null) {
                registerItem("", mainItem!!)
            }

            if (pickaxeItem != null) {
                registerItem("pickaxe", pickaxeItem!!)
            }

            if (swordItem != null) {
                registerItem("sword", swordItem!!)
            }

            if (shovelItem != null) {
                registerItem("shovel", shovelItem!!)
            }

            if (axeItem != null) {
                registerItem("axe", axeItem!!)
            }

            if (hoeItem != null) {
                registerItem("hoe", hoeItem!!)
            }

            if (helmet != null) {
                registerItem("helmet", helmet!!)
            }

            if (chest != null) {
                registerItem("chest", chest!!)
            }

            if (legs != null) {
                registerItem("legs", legs!!)
            }

            if (feet != null) {
                registerItem("boots", feet!!)
            }

            return Materials(props.modId, props.materialId, mainItem, pickaxeItem, swordItem, shovelItem, axeItem, hoeItem, helmet, chest, legs, feet)
        }

        private fun tabSetup(item: Item, itemId: String = "") {
            var iId = itemId.lowercase()

            if (iId.isNotEmpty()) iId = "_$iId"

            val fullId = props.materialId + iId

            props.primaryTabFor.forEach {
                val id = it.key
                if (id == fullId) ServiceProvider.tabHelper.addItemToTab(item, it.value)
            }

            if (!props.primaryTabFor.containsKey(fullId) && props.mainTab != null)
                ServiceProvider.tabHelper.addItemToTab(item, props.mainTab)
        }

        private fun <T: Item> registerItem(str: String, obj: T): T = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(
                BuiltInRegistries.ITEM.key(),
                resloc(props.modId, if (str.isNotEmpty()) "${props.materialId}_$str" else props.materialId)
            ),
            obj
        )
    }
}