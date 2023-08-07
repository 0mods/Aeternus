package team.zeds.ancientmagic.common.api.block.mutli.entity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.common.api.block.BlockEntityBase
import team.zeds.ancientmagic.common.platform.AMServices

class MultiModuleBlockEntity(blockPos: BlockPos, state: BlockState) : BlockEntityBase<MultiModuleBlockEntity>(
    AMServices.PLATFORM.getIAMRegistryEntry().getMultiModuleBlockEntity(),
    blockPos, state
) {
    var corePos: BlockPos? = null

    fun breakMultiBlock() {
        if (level == null || corePos == null) return

        level!!.getBlockEntity(corePos!!)?.let {
            val tile = it as MultiCoreBlockEntity

            if(!tile.isDestroying) {
                tile.breakMultiBlock()
            }
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        if (corePos != null) {
            tag.putInt("coreX", corePos!!.x)
            tag.putInt("coreY", corePos!!.y)
            tag.putInt("coreZ", corePos!!.z)
        }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("coreX") && tag.contains("coreY") && tag.contains("coreZ")) {
            corePos = BlockPos(tag.getInt("coreX"), tag.getInt("coreY"), tag.getInt("coreZ"))
        }
    }
}