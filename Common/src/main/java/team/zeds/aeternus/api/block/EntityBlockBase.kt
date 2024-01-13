package team.zeds.aeternus.api.block

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.FurnaceBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import team.zeds.aeternus.api.block.blockentity.IBlockEntity
import team.zeds.aeternus.api.block.blockentity.IMenued
import team.zeds.aeternus.api.block.blockentity.IStorable
import team.zeds.aeternus.api.block.blockentity.ITickBlockEntity


abstract class EntityBlockBase(private val blockEntity: (BlockPos, BlockState) -> BlockEntity, properties: Properties): BaseEntityBlock(properties) {

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity = blockEntity.invoke(p0, p1)

    override fun <T : BlockEntity?> getTicker(
            level: Level,
            state: BlockState,
            type: BlockEntityType<T>
    ): BlockEntityTicker<T>? = BlockEntityTicker { lvl, blockPos, blockState, entity ->
        if (entity is ITickBlockEntity<*>) {
            if (lvl.isClientSide())
                (entity as ITickBlockEntity<T>).tickOnClient(lvl, blockPos, blockState, entity)
            else (entity as ITickBlockEntity<T>).tickOnServer(lvl, blockPos, blockState, entity)
        }
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        val be = level.getBlockEntity(pos)
        if (be is IBlockEntity)
            be.onPlace(level, state, oldState, isMoving)
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        val be = level.getBlockEntity(pos)
        if (be is IBlockEntity)
            be.onRemove(level, state, oldState, isMoving)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        val be = level.getBlockEntity(pos)
        if (be is IBlockEntity)
            be.onPlacedBy(level, state, placer, stack)
    }

    override fun playerDestroy(level: Level, player: Player, pos: BlockPos, state: BlockState, be: BlockEntity?, stack: ItemStack) {
        if (be is IStorable) {
            val stack1 = be.fromStorageToStack(ItemStack(this))
            Block.popResource(level, pos, stack1)
            player.awardStat(Stats.BLOCK_MINED.get(this))
            player.causeFoodExhaustion(0.005F)
        } else {
            super.playerDestroy(level, player, pos, state, be, stack)
        }
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hitResult: BlockHitResult): InteractionResult {
        val be = level.getBlockEntity(pos)
        if (be is IMenued) {
            val prov = object: MenuProvider {
                override fun createMenu(p0: Int, p1: Inventory, p2: Player): AbstractContainerMenu? =
                        be.getContainer(p0, p1, p2, be)

                override fun getDisplayName(): Component = this@EntityBlockBase.name
            }

            val container = prov.createMenu(0, player.inventory, player)
            if (container != null) {
                if (player is ServerPlayer) player.openMenu(prov)

                return InteractionResult.SUCCESS
            }
        }

        return super.use(state, level, pos, player, hand, hitResult)
    }
}
