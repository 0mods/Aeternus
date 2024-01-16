package team.zeds.aeternus.api.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.FurnaceBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class BlockBuilder private constructor() {
    private lateinit var useCtx: UseOnContext.() -> InteractionResult
    private val properties = Properties.of()
    private lateinit var tooltips: (stack: ItemStack, getter: BlockGetter?, tooltips: MutableList<Component>, flag: TooltipFlag) -> Unit
    private lateinit var blockEntity: (BlockPos, BlockState) -> BlockEntity
    private lateinit var displayName: Component
    private lateinit var onRemoveF: (state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoved: Boolean) -> Unit
    private lateinit var onPlaceF: (state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoved: Boolean) -> Unit

    fun tooltip(tooltip: (stack: ItemStack, getter: BlockGetter?, tooltips: MutableList<Component>, flag: TooltipFlag) -> Unit): team.zeds.aeternus.api.block.BlockBuilder {
        this.tooltips = tooltip
        return this
    }

    fun tooltip(vararg tooltip: Component): team.zeds.aeternus.api.block.BlockBuilder = this.tooltip { _, _, texts, _ -> texts.addAll(tooltip) }

    fun properties(props: Properties.() -> Unit): team.zeds.aeternus.api.block.BlockBuilder {
        this.properties.apply(props)
        return this
    }

    fun entity(func: (BlockPos, BlockState) -> BlockEntity): team.zeds.aeternus.api.block.BlockBuilder {
        this.blockEntity = func
        return this
    }

    fun onRemove(func: (state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoved: Boolean) -> Unit): team.zeds.aeternus.api.block.BlockBuilder {
        this.onRemoveF = func
        return this
    }

    fun onPlace(func: (state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoved: Boolean) -> Unit): team.zeds.aeternus.api.block.BlockBuilder {
        this.onPlaceF = func
        return this
    }

    fun use(func: UseOnContext.() -> InteractionResult): team.zeds.aeternus.api.block.BlockBuilder {
        this.useCtx = func
        return this
    }

    fun buildAndRegister(name: ResourceLocation): Block = Registry.register(BuiltInRegistries.BLOCK, name, build())

    fun build(): BaseEntityBlock = object : BaseEntityBlock(properties) {
        @Deprecated("Deprecated in Java")
        override fun use(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            player: Player,
            hand: InteractionHand,
            result: BlockHitResult
        ): InteractionResult {
            val useContext = UseOnContext(level, player, hand, player.getItemInHand(hand), result)
            return if (::useCtx.isInitialized) useCtx.invoke(useContext) else super.use(state, level, pos, player, hand, result)
        }

        override fun getDescriptionId(): String = if (::displayName.isInitialized) displayName.toString() else super.getDescriptionId()

        override fun appendHoverText(
            stack: ItemStack,
            getter: BlockGetter?,
            tooltipList: MutableList<Component>,
            flag: TooltipFlag
        ) {
            if (::tooltips.isInitialized)
                tooltips.invoke(stack, getter, tooltipList, flag)
        }

        override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity? =
            if (::blockEntity.isInitialized) blockEntity.invoke(p0, p1) else null

        override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoved: Boolean) {
            if (::onRemoveF.isInitialized) onRemoveF.invoke(state, level, pos, newState, isMoved)
            else super.onRemove(state, level, pos, newState, isMoved)
        }

        override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, isMoved: Boolean) {
            if (::onPlaceF.isInitialized) onPlaceF.invoke(state, level, pos, oldState, isMoved) else super.onPlace(state, level, pos, oldState, isMoved)
        }

        override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { this }
    }

    companion object {
        @JvmStatic
        fun get(): team.zeds.aeternus.api.block.BlockBuilder = team.zeds.aeternus.api.block.BlockBuilder()
    }
}