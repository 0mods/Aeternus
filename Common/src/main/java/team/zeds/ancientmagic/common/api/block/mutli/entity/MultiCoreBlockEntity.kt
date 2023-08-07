package team.zeds.ancientmagic.common.api.block.mutli.entity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.state.BlockState
import org.joml.Vector3d
import team.zeds.ancientmagic.common.api.block.BlockEntityBase
import team.zeds.ancientmagic.common.api.block.mutli.data.MultiBlockStorage
import team.zeds.ancientmagic.common.platform.AMServices

class MultiCoreBlockEntity(blockPos: BlockPos, state: BlockState) : BlockEntityBase<MultiCoreBlockEntity>(
    AMServices.PLATFORM.getIAMRegistryEntry().getMultiCoreBlockEntity(),
    blockPos, state
) {
    var onOpen: (Player, BlockPos) -> Unit = { _, _ -> }
    var name = "3d_print"
    var offset = Vector3d(1.0, 0.0, 0.0)
    var modules: MutableList<BlockPos> = mutableListOf()
    var isDestroying = false

    fun breakMultiBlock() {
        if (level == null) return
        isDestroying = true

        modules.forEach {
            level!!.destroyBlock(it, false)
        }

        level!!.destroyBlock(worldPosition, false)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        if (modules.isNotEmpty()) {
            tag.putInt("modulesCount", modules.size)
            for (i in modules.indices) {
                tag.putInt("moduleX$i", modules[i].x)
                tag.putInt("moduleY$i", modules[i].y)
                tag.putInt("moduleZ$i", modules[i].z)
            }
        }
        tag.putString("name", name)
        tag.putDouble("offsetX", offset.x)
        tag.putDouble("offsetY", offset.y)
        tag.putDouble("offsetZ", offset.z)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("modulesCount")) {
            val modulesCount = tag.getInt("modulesCount")
            for (i in 0 until modulesCount) {
                modules.add(BlockPos(tag.getInt("moduleX$i"), tag.getInt("moduleY$i"), tag.getInt("moduleZ$i")))
            }
        }
        name = tag.getString("name")
        onOpen = MultiBlockStorage.multiBlockActions[name] ?: { _, _ -> }
        offset = Vector3d(tag.getDouble("offsetX"), tag.getDouble("offsetY"), tag.getDouble("offsetZ"))
    }

    //Here GECOLIB code
}