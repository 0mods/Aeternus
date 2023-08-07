package team.zeds.ancientmagic.common.api.block

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.common.api.block.settings.TickedBlockEntity
import team.zeds.ancientmagic.common.api.helper.BlockEntityHelper

open class BlockEntityBase<T: BlockEntityBase<T>>(blockEntityType: BlockEntityType<*>, blockPos: BlockPos, state: BlockState) :
    BlockEntity(blockEntityType, blockPos, state), TickedBlockEntity<T> {
    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket =
        ClientboundBlockEntityDataPacket.create(this, BlockEntity::saveWithFullMetadata)
    override fun getUpdateTag(): CompoundTag = this.saveWithFullMetadata()

    fun changeX() {
        super.setChanged()
        BlockEntityHelper.instance!!.sendToPlayers(this)
    }
}