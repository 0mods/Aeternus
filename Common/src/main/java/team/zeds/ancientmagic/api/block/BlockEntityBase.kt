package team.zeds.ancientmagic.api.block

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.api.helper.BlockEntityHelper

open class BlockEntityBase(blockEntityType: BlockEntityType<*>, blockPos: BlockPos, state: BlockState) :
    BlockEntity(blockEntityType, blockPos, state) {
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this, BlockEntity::saveWithFullMetadata)
    override fun getUpdateTag(): CompoundTag = this.saveWithFullMetadata()

    fun changeX() {
        super.setChanged()
        BlockEntityHelper.instance!!.sendToPlayers(this)
    }
}