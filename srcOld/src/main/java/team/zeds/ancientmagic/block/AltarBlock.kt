package team.zeds.ancientmagic.block

import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import team.zeds.ancientmagic.api.block.EntityBlockBase
import team.zeds.ancientmagic.api.helper.StackHelper
import team.zeds.ancientmagic.api.helper.VoxelShapeBuilder
import team.zeds.ancientmagic.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.init.registries.AMRegister

@Suppress("OVERRIDE_DEPRECATION")
class AltarBlock: EntityBlockBase(Properties.copy(Blocks.STONE)) {
    val voxelModel = VoxelShapeBuilder.builder()
        .cube(2.0, 0.0, 2.0, 14.0, 1.0, 14.0)
        .cube(1.5, 0.6000000000000001, 1.5, 14.5, 8.2, 14.8)
        .cube(3.0, 8.1, 3.0, 13.0, 8.700000000000001, 13.3)
        .cube(4.0, 8.700000000000001, 4.0, 12.0, 8.9, 12.0)
        .cube(2.0, 13.299999999999997, 7.0, 4.0, 15.299999999999997, 9.0)
        .cube(1.0, 8.199999999999996, 7.0, 3.0, 13.299999999999997, 9.0)
        .cube(7.0, 8.199999999999998, 13.300000000000004, 9.0, 13.299999999999999, 15.299999999999994)
        .cube(7.0, 13.299999999999999, 12.325000000000005, 9.0, 15.299999999999999, 14.325000000000003)
        .cube(11.975000000000007, 13.299999999999999, 7.0, 13.975000000000007, 15.299999999999999, 9.0)
        .cube(13.000000000000007, 8.199999999999998, 7.0, 14.999999999999991, 13.299999999999999, 9.0)
        .cube(7.0, 13.399999999999999, 2.1000000000000005, 9.0, 15.399999999999999, 4.1)
        .cube(7.0, 8.3, 1.0, 9.0, 13.399999999999999, 3.000000000000001)
        .of()

    override fun getShape(
        state: BlockState,
        getter: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return voxelModel
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = AltarBlockEntity(pos, state)

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        entityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return ticker(entityType, AMRegister.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntity::tick)
    }

    override fun use(blockState: BlockState, level: Level, blockPos: BlockPos, player: Player, hand: InteractionHand,
                     hitResult: BlockHitResult): InteractionResult {
        val blockEntity = level.getBlockEntity(blockPos)

        if (blockEntity is AltarBlockEntity) {
            val inv = blockEntity.getInv()
            val input = inv.getStackInSlot(0)
            val output = inv.getStackInSlot(1)

            if (!output.isEmpty) {
                val item = ItemEntity(level, player.x, player.y, player.z, output)

                item.setNoPickUpDelay()
                level.addFreshEntity(item)
                inv.setStackInSlot(1, ItemStack.EMPTY)
            } else {
                val itemHand = player.getItemInHand(hand)
                if (input.isEmpty && !itemHand.isEmpty) {
                    inv.setStackInSlot(0, StackHelper.withSize(itemHand, 1, false))
                    player.setItemInHand(hand, StackHelper.shrink(itemHand, 1, false))
                    level.playSound(null, blockPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f)
                } else if (!input.isEmpty) {
                    val item = ItemEntity(level, player.x, player.y, player.z, output)
                    item.setNoPickUpDelay()
                    level.addFreshEntity(item)
                    inv.setStackInSlot(0, ItemStack.EMPTY)
                }
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun onRemove(oldState: BlockState, level: Level, blockPos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (oldState.block != newState.block) {
            val blockEntity = level.getBlockEntity(blockPos)
            if (blockEntity is AltarBlockEntity) Containers.dropContents(level, blockPos, blockEntity.getInv().stacks)
        }

        super.onRemove(oldState, level, blockPos, newState, isMoving)
    }
}