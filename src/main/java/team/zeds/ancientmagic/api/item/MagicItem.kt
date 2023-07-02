@file:Suppress("SENSELESS_COMPARISON")

package team.zeds.ancientmagic.api.item

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import team.zeds.ancientmagic.api.cap.ItemStackMagic
import team.zeds.ancientmagic.api.magic.MagicType
import team.zeds.ancientmagic.api.magic.MagicType.MagicClassifier
import team.zeds.ancientmagic.api.magic.MagicTypes
import kotlin.math.max
import kotlin.math.min

interface IMagicItem {
    fun useMT(level: Level, player: Player, hand: InteractionHand)
    fun useOnMT(use: UseOnContext)
}

open class MagicItem(private val builder: MagicItemBuilder): Item(builder.getProperties()), IMagicItem, ItemStackMagic {
    private var canUseItem = true

    init {
        this.builder.getProperties().setNoRepair()
    }

    @Deprecated("Use \"useMT\" in MagicItem")
    override fun use(
        level: Level,
        player: Player,
        hand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (!stack.`is`(this)) return InteractionResultHolder.fail(stack)
        if (this.getItemUse()) {
            if (this.getStorageMana(stack) != 0 && getBuilder().getMaxMana() != 0)  {
                subMana(this.getBuilder().getSubMana(), stack)
                this.useMT(level, player, hand)
                return InteractionResultHolder.success(stack)
            } else if (getBuilder().getMaxMana() != 0 && this.getStorageMana(stack) == 0) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true)
                return InteractionResultHolder.fail(stack)
            } else if (getBuilder().getMaxMana() == 0) {
                this.useMT(level, player, hand)
                return InteractionResultHolder.success(stack)
            }
        }
        return InteractionResultHolder.pass(stack)
    }

    @Deprecated("Use \"useOn\" in MagicItem")
    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player!!
        val stack = player.getItemInHand(context.hand)

        if (this.getItemUse()) {
            if (this.getStorageMana(stack) != 0 && getBuilder().getMaxMana() != 0) {
                subMana(this.getBuilder().getSubMana(), stack)
                this.useOnMT(context)
                return InteractionResult.SUCCESS
            } else if (getBuilder().getMaxMana() != 0 && this.getStorageMana(stack) == 0) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true)
                return InteractionResult.FAIL
            } else if (getBuilder().getMaxMana() == 0) {
                this.useOnMT(context)
                return InteractionResult.SUCCESS
            }
        }
        return InteractionResult.PASS
    }

    override fun useMT(level: Level, player: Player, hand: InteractionHand) {}

    override fun useOnMT(use: UseOnContext) {}

    fun getBuilder(): MagicItemBuilder {
        return builder
    }

    fun getItemUse(): Boolean {
        return canUseItem
    }

    fun setItemUse(canUse: Boolean) {
        canUseItem = canUse
    }

    override fun getMagicType(): MagicType {
        return this.getBuilder().getMagicType()
    }

    override fun getMagicSubtype(): MagicType {
        return this.getBuilder().getMagicSubtype()
    }

    override fun getMaxMana(): Int {
        return this.getBuilder().getMaxMana()
    }

    override fun getStorageMana(stack: ItemStack): Int {
        return if (stack.orCreateTag.get("StorageMana") != null) {
            stack.orCreateTag.getInt("StorageMana")
        } else 0
    }

    override fun setStorageMana(mana: Int, stack: ItemStack) {
        stack.orCreateTag.putInt("StorageMana", mana)
    }

    override fun addMana(count: Int, stack: ItemStack) {
        val storage = this.getStorageMana(stack)
        val calc = min(storage + count, getMaxMana())
        setStorageMana(calc, stack)
    }

    override fun subMana(count: Int, stack: ItemStack) {
        val storage = this.getStorageMana(stack)
        val calc = max(storage - count, 0)
        setStorageMana(calc, stack)
    }

    companion object {
        @JvmStatic
        fun callBuilder() : MagicItemBuilder {
            return MagicItemBuilder.get()
        }
    }
}

open class MagicBlockItem(block: Block, private var builder: MagicItemBuilder): BlockItem(block, builder.getProperties()), ItemStackMagic, IMagicItem {
    private var canUseItem = true

    init {
        this.builder.getProperties().setNoRepair()
    }

    @Deprecated("Use \"useMT\" in MagicItem")
    override fun use(
        level: Level,
        player: Player,
        hand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (!stack.`is`(this)) return InteractionResultHolder.fail(stack)
        if (this.getItemUse()) {
            if (this.getStorageMana(stack) != 0 && getBuilder().getMaxMana() != 0)  {
                subMana(this.getBuilder().getSubMana(), stack)
                this.useMT(level, player, hand)
                return InteractionResultHolder.success(stack)
            } else if (getBuilder().getMaxMana() != 0 && this.getStorageMana(stack) == 0) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true)
                return InteractionResultHolder.fail(stack)
            } else if (getBuilder().getMaxMana() == 0) {
                this.useMT(level, player, hand)
                return InteractionResultHolder.success(stack)
            }
        }
        return InteractionResultHolder.pass(stack)
    }

    @Deprecated("Use \"useOn\" in MagicItem")
    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player!!
        val stack = player.getItemInHand(context.hand)

        if (this.getItemUse()) {
            if (this.getStorageMana(stack) != 0 && getBuilder().getMaxMana() != 0) {
                subMana(this.getBuilder().getSubMana(), stack)
                this.useOnMT(context)
                return InteractionResult.SUCCESS
            } else if (getBuilder().getMaxMana() != 0 && this.getStorageMana(stack) == 0) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true)
                return InteractionResult.FAIL
            } else if (getBuilder().getMaxMana() == 0) {
                this.useOnMT(context)
                return InteractionResult.SUCCESS
            }
        }
        return InteractionResult.PASS
    }

    override fun useMT(level: Level, player: Player, hand: InteractionHand) {}

    override fun useOnMT(use: UseOnContext) {}

    fun getBuilder(): MagicItemBuilder {
        return builder
    }

    fun getItemUse(): Boolean {
        return canUseItem
    }

    fun setItemUse(canUse: Boolean) {
        canUseItem = canUse
    }

    override fun getMagicType(): MagicType {
        return this.getBuilder().getMagicType()
    }

    override fun getMagicSubtype(): MagicType {
        return this.getBuilder().getMagicSubtype()
    }

    override fun getMaxMana(): Int {
        return this.getBuilder().getMaxMana()
    }

    override fun getStorageMana(stack: ItemStack): Int {
        return if (stack.orCreateTag.get("StorageMana") != null) {
            stack.orCreateTag.getInt("StorageMana")
        } else 0
    }

    override fun setStorageMana(mana: Int, stack: ItemStack) {
        stack.orCreateTag.putInt("StorageMana", mana)
    }

    override fun addMana(count: Int, stack: ItemStack) {
        val storage = this.getStorageMana(stack)
        val calc = min(storage + count, getMaxMana())
        setStorageMana(calc, stack)
    }

    override fun subMana(count: Int, stack: ItemStack) {
        val storage = this.getStorageMana(stack)
        val calc = max(storage - count, 0)
        setStorageMana(calc, stack)
    }

    override fun placeBlock(context: BlockPlaceContext, state: BlockState): Boolean {
        val player = context.player
        val collisionContext = if (player == null) CollisionContext.empty() else CollisionContext.of(player)

        return (getItemUse()) && (!this.mustSurvive() || state.canSurvive(context.level, context.clickedPos))
                && context.level.isUnobstructed(state, context.clickedPos, collisionContext)
    }

    companion object {
        @JvmStatic
        fun callBuilder() : MagicItemBuilder {
            return MagicItemBuilder.get()
        }
    }
}

@Suppress("unused")
open class MagicItemBuilder private constructor() {
    private var properties = Item.Properties()

    private var magicType: MagicType = MagicTypes.LOW_MAGIC

    private var magicSubtype: MagicType = MagicTypes.NOTHING
    private var maxMana = 0
    private var subManaIfUse = 1

    fun setMaxMana(maxMana: Int): MagicItemBuilder {
        this.maxMana = maxMana
        return this
    }

    fun setSubManaIfUse(subManaIfUse: Int): MagicItemBuilder {
        this.subManaIfUse = subManaIfUse
        return this
    }

    fun fireProof(): MagicItemBuilder {
        properties.fireResistant()
        return this
    }

    fun setFood(properties: FoodProperties): MagicItemBuilder {
        this.properties.food(properties)
        return this
    }

    fun setMagicType(type: MagicType): MagicItemBuilder {
        if (type.getClassifier() == MagicClassifier.MAIN_TYPE) {
            magicType = type
            return this
        } else throw RuntimeException("Classifier ${type.getClassifier().declaringJavaClass} isn't ${MagicClassifier.MAIN_TYPE}. Accepts only ${MagicClassifier.MAIN_TYPE}!")
    }

    fun setMagicSubtype(type: MagicType): MagicItemBuilder {
        if (type.getClassifier() == MagicClassifier.SUBTYPE && this.getMagicType() != null) {
            magicSubtype = type
            return this
        } else throw RuntimeException("Classifier ${type.getClassifier().declaringJavaClass} isn't ${MagicClassifier.SUBTYPE}. Accepts only ${MagicClassifier.SUBTYPE}!")
    }

    fun setRarity(rarity: Rarity): MagicItemBuilder {
        properties.rarity(rarity)
        return this
    }

    fun setRemainder(itemToRemained: Item): MagicItemBuilder {
        properties.craftRemainder(itemToRemained)
        return this
    }

    fun setStacks(stacksTo: Int): MagicItemBuilder {
        properties.stacksTo(stacksTo)
        return this
    }

    fun getMaxMana() : Int {
        return if (maxMana >= 0) maxMana else throw RuntimeException("${maxMana.javaClass} may not be a lower than 0")
    }

    fun getSubMana() : Int = subManaIfUse

    fun getMagicType() : MagicType = magicType

    fun getMagicSubtype() : MagicType = magicSubtype

    fun getProperties(): Item.Properties = properties

    companion object {
        @JvmStatic
        fun get(): MagicItemBuilder {
            return MagicItemBuilder()
        }
    }
}