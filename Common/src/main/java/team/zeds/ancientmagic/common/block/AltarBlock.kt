package team.zeds.ancientmagic.common.block

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
import team.zeds.ancientmagic.common.api.block.EntityBlockBase
import team.zeds.ancientmagic.common.api.helper.VoxelShapeBuilder
import team.zeds.ancientmagic.common.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.common.platform.AMServices

@Suppress("OVERRIDE_DEPRECATION")
class AltarBlock: EntityBlockBase(Properties.copy(Blocks.STONE).noOcclusion()) {
    val voxelModel = VoxelShapeBuilder.builder()
        .cube(2.0, 0.0, 2.0, 14.0, 1.0, 14.0)
        .cube(1.5, 0.6, 1.5, 14.5, 8.2, 14.8)
        .cube(3.0, 8.1, 3.0, 13.0, 8.7, 13.3)
        .cube(4.0, 8.7, 4.0, 12.0, 8.9, 12.0)
        .cube(2.0, 13.3, 7.0, 4.0, 15.3, 9.0)
        .cube(1.0, 8.2, 7.0, 3.0, 13.3, 9.0)
        .cube(7.0, 8.2, 13.3, 9.0, 13.3, 15.3)
        .cube(7.0, 13.3, 12.3, 9.0, 15.3, 14.3)
        .cube(12.0, 13.3, 7.0, 13.98, 15.3, 9.0)
        .cube(13.0, 8.2, 7.0, 15.0, 13.3, 9.0)
        .cube(7.0, 13.4, 2.1, 9.0, 15.4, 4.1)
        .cube(7.0, 8.3, 1.0, 9.0, 13.4, 3.0)
        .of()

    override fun getShape(
        state: BlockState,
        getter: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return voxelModel
    }

    override fun newBlockEntity(var1: BlockPos, var2: BlockState): BlockEntity = AltarBlockEntity(var1, var2)

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand,
                     hitResult: BlockHitResult
    ): InteractionResult {
        val blockEntity = level.getBlockEntity(pos)

        if (blockEntity is AltarBlockEntity) {
            val inv = blockEntity.getInv()
            val input = inv.getStackInSlotHandler(0)
            val output = inv.getStackInSlotHandler(1)

            if (!output.isEmpty) {
                inv.setStackInSlot(1, ItemStack.EMPTY)
                val item = ItemEntity(level, player.x, player.y, player.z, output)

                item.setNoPickUpDelay()
                level.addFreshEntity(item)
            } else {
                val itemHand = player.getItemInHand(hand)
                if (input.isEmpty && !itemHand.isEmpty) {
                    inv.setStackInSlot(0, AMServices.PLATFORM.getIStackHelper().iWithSize(itemHand, 1, false))
                    player.setItemInHand(hand, AMServices.PLATFORM.getIStackHelper().iShrink(itemHand, 1, false))
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f)
                } else if (!input.isEmpty) {
                    inv.setStackInSlot(0, ItemStack.EMPTY)
                    val item = ItemEntity(level, player.x, player.y, player.z, output)

                    item.setNoPickUpDelay()
                    level.addFreshEntity(item)
                }
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun onRemove(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (oldState.block != newState.block) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is AltarBlockEntity) Containers.dropContents(level, pos, blockEntity.getInv().getStacks())
        }

        super.onRemove(oldState, level, pos, newState, isMoving)
    }

    override fun <T : BlockEntity> serverTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? = createTicker(
        type,
        AMServices.PLATFORM.getIAMRegistryEntry().getAltarBlockEntityType(),
        AltarBlockEntity::tick
    )
}