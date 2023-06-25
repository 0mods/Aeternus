@file:Suppress("unused", "DEPRECATION", "SENSELESS_COMPARISON")

package team.zeds.ancientmagic.api

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.flag.FeatureFlag
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.minecraft.world.phys.BlockHitResult
import team.zeds.ancientmagic.api.atomic.KAtomicUse
import team.zeds.ancientmagic.api.magic.MagicType
import team.zeds.ancientmagic.api.magic.MagicType.MagicClassifier
import team.zeds.ancientmagic.api.magic.MagicTypes
import team.zeds.ancientmagic.init.AMCapability
import java.util.function.Function
import java.util.function.Supplier
import java.util.function.ToIntFunction

@FunctionalInterface
interface IMagicBlock {
    fun use(use: KAtomicUse<*>)
}

interface IMagicItem {
    fun use(use: KAtomicUse<ItemStack>)
    fun useOn(use: KAtomicUse<*>)
}

open class MagicBlock(builder: MagicBuilder): Block(builder.getProperties()), EntityBlock, IMagicBlock {
    protected val blockEntity: BlockEntityType<out MagicBlockEntity?>?

    init {
        this.blockEntity = builder.getBlockEntityType()
    }

    @Deprecated("Deprecated in Java")
    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        result: BlockHitResult,
    ): InteractionResult {
        val atomicUse = KAtomicUse<Any>(state, level, pos, player, hand, result)
        this.use(atomicUse)
        return atomicUse.returnResult!!
    }

    override fun use(use: KAtomicUse<*>) {}

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? =
        if (this.blockEntity != null) MagicBlockEntity(this.blockEntity, pos, state) else null


    open class MagicBuilder private constructor() {
        @JvmField var properties: Properties = Properties.of()
        @JvmField var magicType: MagicType = MagicTypes.LOW_MAGIC
        @JvmField var magicSubtype: MagicType? = null
        @JvmField var blockEntity: BlockEntityType<out MagicBlockEntity?>? = null
        
        fun get() : MagicBuilder {
            return MagicBuilder()
        }

        @Deprecated("")
        fun setProperties(properties: Properties): MagicBuilder {
            this.properties = properties
            return this
        }

        fun make(): MagicBuilder {
            return setProperties(Properties.of())
        }

        fun make(copyFrom: BlockBehaviour): MagicBuilder {
            return setProperties(Properties.copy(copyFrom))
        }

        @Deprecated("")
        fun getProperties(): Properties {
            return properties
        }

        fun unCollised(): MagicBuilder {
            getProperties().noCollission()
            return this
        }

        fun unOcculised(): MagicBuilder {
            getProperties().noOcclusion()
            return this
        }

        fun friction(friction: Float): MagicBuilder {
            getProperties().friction(friction)
            return this
        }

        fun speedBuf(numOfBuf: Float): MagicBuilder {
            getProperties().speedFactor(numOfBuf)
            return this
        }

        fun jumpBuf(numOfBuf: Float): MagicBuilder {
            getProperties().speedFactor(numOfBuf)
            return this
        }

        fun sound(type: SoundType): MagicBuilder {
            getProperties().sound(type)
            return this
        }

        fun light(state: ToIntFunction<BlockState?>): MagicBuilder {
            getProperties().lightLevel(state)
            return this
        }

        fun strength(destroyTime: Float, explosionResistant: Float): MagicBuilder {
            getProperties().strength(destroyTime, explosionResistant)
            return this
        }

        fun strength(destroyTimeAndResistant: Float): MagicBuilder {
            return this.strength(destroyTimeAndResistant, destroyTimeAndResistant)
        }

        fun instabreak(): MagicBuilder {
            getProperties().instabreak()
            return this
        }

        fun unbreak(): MagicBuilder {
            return destory(-1f)
        }

        fun randomTick(): MagicBuilder {
            getProperties().randomTicks()
            return this
        }

        fun dynamicShape(): MagicBuilder {
            getProperties().dynamicShape()
            return this
        }

        fun noLoot(): MagicBuilder {
            getProperties().noLootTable()
            return this
        }

        @Deprecated("")
        fun drops(block: Block): MagicBuilder {
            getProperties().dropsLike(block)
            return this
        }

        fun drops(blockIn: Supplier<Block?>): MagicBuilder {
            getProperties().lootFrom(blockIn)
            return this
        }

        fun ignitedByLava(): MagicBuilder {
            getProperties().ignitedByLava()
            return this
        }

        fun liquid(): MagicBuilder {
            getProperties().liquid()
            return this
        }

        fun forceSolidOn(): MagicBuilder {
            getProperties().forceSolidOn()
            return this
        }

        @Deprecated("")
        fun forceSolidOff(): MagicBuilder {
            getProperties().forceSolidOff()
            return this
        }

        fun onPush(reaction: PushReaction): MagicBuilder {
            getProperties().pushReaction(reaction)
            return this
        }

        fun air(): MagicBuilder {
            getProperties().air()
            return this
        }

        fun validSpawn(predicate: StateArgumentPredicate<EntityType<*>?>): MagicBuilder {
            getProperties().isValidSpawn(predicate)
            return this
        }

        fun redstoneConductor(predicate: StatePredicate): MagicBuilder {
            getProperties().isRedstoneConductor(predicate)
            return this
        }

        fun suffocating(predicate: StatePredicate): MagicBuilder {
            getProperties().isSuffocating(predicate)
            return this
        }

        fun viewBlocking(predicate: StatePredicate): MagicBuilder {
            getProperties().isViewBlocking(predicate)
            return this
        }

        fun postProcess(predicate: StatePredicate): MagicBuilder {
            getProperties().hasPostProcess(predicate)
            return this
        }

        fun emissiveRendering(predicate: StatePredicate): MagicBuilder {
            getProperties().emissiveRendering(predicate)
            return this
        }

        fun requiresToolForDrop(): MagicBuilder {
            getProperties().requiresCorrectToolForDrops()
            return this
        }

        fun color(color: Function<BlockState?, MapColor?>): MagicBuilder {
            getProperties().mapColor(color)
            return this
        }

        fun color(color: DyeColor): MagicBuilder {
            getProperties().mapColor(color)
            return this
        }

        fun color(color: MapColor): MagicBuilder {
            getProperties().mapColor(color)
            return this
        }

        fun destory(time: Float): MagicBuilder {
            getProperties().destroyTime(time)
            return this
        }

        fun explosionResistance(resistance: Float): MagicBuilder {
            getProperties().explosionResistance(resistance)
            return this
        }

        fun offsetType(type: OffsetType): MagicBuilder {
            getProperties().offsetType(type)
            return this
        }

        fun noParticle(): MagicBuilder {
            getProperties().noParticlesOnBreak()
            return this
        }

        fun requiredFeatures(vararg flags: FeatureFlag): MagicBuilder {
            getProperties().requiredFeatures(*flags)
            return this
        }

        fun instrument(instrument: NoteBlockInstrument): MagicBuilder {
            getProperties().instrument(instrument)
            return this
        }

        fun replace(): MagicBuilder {
            getProperties().replaceable()
            return this
        }

        fun setMagicType(type: MagicType): MagicBuilder {
            magicType = type
            return this
        }

        fun setMagicSubtype(subtype: MagicType): MagicBuilder {
            return if (getMagicType() != null && subtype.getClassifier() == MagicClassifier.SUBTYPE) {
                magicSubtype = subtype
                this
            } else throw RuntimeException("Main magic type is null or chose subtype is not subtype!")
        }

        protected fun setMaxMana(maxMana: Int, entity: MagicBlockEntity): MagicBuilder {
            entity.updateTag.putInt("MaxManaStorage", maxMana)
            return this
        }

        fun setBlockEntityType(entityType: BlockEntityType<out MagicBlockEntity>): MagicBuilder {
            blockEntity = entityType
            return this
        }

        fun getBlockEntityType(): BlockEntityType<out MagicBlockEntity> {
            return blockEntity
                ?: throw NullPointerException(
                    String.format(
                        "Block Entity type %s is null",
                        getBlockEntityType()
                    )
                )
        }

        fun getBlockEntity(block: BlockGetter, pos: BlockPos): MagicBlockEntity {
            return getBlockEntityType().getBlockEntity(
                block,
                pos
            )!!
        }

        private fun getMagicType(): MagicType {
            return magicType
        }

        private fun getMagicSubtype(): MagicType {
            return magicSubtype!!
        }

        fun getMaxMana(entity: MagicBlockEntity): Int {
            return entity.updateTag.getInt("MaxManaStorage")
        }

        fun getManaCount(entity: MagicBlockEntity): Int {
            return entity.updateTag.getInt("ManaStorage")
        }
    }

    class MagicBlockEntity(blockEntity: BlockEntityType<*>, pos: BlockPos, state: BlockState)
        : BlockEntity(blockEntity, pos, state)
}

open class MagicItem(private val builder: MagicBuilder): Item(builder.getProperties()), IMagicItem {
    private var canUseItem = true

    init {
        this.builder.getProperties().setNoRepair()
    }

    @Deprecated("")
    override fun use(
        level: Level,
        player: Player,
        hand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val atomicUse: KAtomicUse<ItemStack> = KAtomicUse(player, level, hand)
        val stack = atomicUse.stack
        if (!stack.`is`(this)) return InteractionResultHolder.fail(stack)
        if (getItemUse()) {
            if (getBuilder().getManaCount() != 0 && getBuilder().getManaCount() != 0) {
                stack.getCapability(AMCapability.MAGIC_OBJECT)
                    .ifPresent { cap -> cap.subMana(getBuilder().getSubMana()) }
                this.use(atomicUse)
                return atomicUse.returnHolder!!
            } else if (getBuilder().getMaxMana() != 0 && getBuilder().getManaCount() == 0) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true)
                return InteractionResultHolder.fail(stack)
            } else if (getBuilder().getMaxMana() == 0) {
                this.use(atomicUse)
                return atomicUse.returnHolder!!
            }
        }
        return InteractionResultHolder.pass(stack)
    }

    @Deprecated("")
    override fun useOn(use: UseOnContext): InteractionResult {
        val context: KAtomicUse<*> = KAtomicUse<Any>(use)
        val stack = context.stack
        val player = context.player
        if (getItemUse()) {
            if (getBuilder().getManaCount() != 0 && getBuilder().getManaCount() != 0) {
                stack.getCapability(AMCapability.MAGIC_OBJECT)
                    .ifPresent { cap -> cap.subMana(getBuilder().getSubMana()) }
                this.useOn(context)
                return context.returnResult!!
            } else if (getBuilder().getMaxMana() != 0 && getBuilder().getManaCount() == 0) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true)
                return InteractionResult.FAIL
            } else if (getBuilder().getMaxMana() == 0) {
                this.useOn(context)
                return context.returnResult!!
            }
        }
        return InteractionResult.PASS
    }

    override fun use(use: KAtomicUse<ItemStack>) {}

    override fun useOn(use: KAtomicUse<*>) {}

    fun getBuilder(): MagicBuilder {
        return builder
    }

    fun getItemUse(): Boolean {
        return canUseItem
    }

    fun setItemUse(canUse: Boolean) {
        canUseItem = canUse
    }


    @Suppress("unused")
    open class MagicBuilder private constructor() {
        @Deprecated("Move to private")
        @JvmField var properties = Properties()

        @JvmField var magicType: MagicType = MagicTypes.LOW_MAGIC

        @JvmField var magicSubtype: MagicType? = null
        @JvmField var maxMana = 0
        @JvmField var manaCount = 0
        @JvmField var subManaIfUse = 0

        @Deprecated("")
        fun setProperties(properties: Properties): MagicBuilder {
            this.properties = properties
            return this
        }

        fun setMaxMana(maxMana: Int): MagicBuilder {
            this.maxMana = maxMana
            return this
        }

        fun setManaCount(count: Int): MagicBuilder {
            manaCount = count
            return this
        }

        fun setSubManaIfUse(subManaIfUse: Int): MagicBuilder {
            this.subManaIfUse = subManaIfUse
            return this
        }

        fun fireProof(): MagicBuilder {
            properties.fireResistant()
            return this
        }

        fun setFood(properties: FoodProperties): MagicBuilder {
            this.properties.food(properties)
            return this
        }

        fun setMagicType(type: MagicType): MagicBuilder {
            magicType = type
            return this
        }

        fun setMagicSubtype(type: MagicType): MagicBuilder {
            if (type.getClassifier() == MagicClassifier.SUBTYPE) magicSubtype = type
            return this
        }

        fun setRarity(rarity: Rarity): MagicBuilder {
            properties.rarity(rarity)
            return this
        }

        fun setRemainder(itemToRemained: Item): MagicBuilder {
            properties.craftRemainder(itemToRemained)
            return this
        }

        fun setStacks(stacksTo: Int): MagicBuilder {
            properties.stacksTo(stacksTo)
            return this
        }

        fun getManaCount() : Int = manaCount

        fun getMaxMana() : Int = maxMana

        fun getSubMana() : Int = subManaIfUse

        fun getMagicType() : MagicType = magicType

        fun getMagicSubtype() : MagicType = magicSubtype!!

        fun getProperties(): Properties = properties

        companion object {
            @JvmStatic
            fun get(): MagicBuilder {
                return MagicBuilder()
            }
        }
    }

}