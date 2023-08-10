package team.zeds.ancientmagic.common.api.block

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.common.api.block.settings.IHandled
import team.zeds.ancientmagic.common.api.helper.IHandleStack

abstract class ContainerBlockEntity<T: BlockEntityBase<T>>(blockEntityType: BlockEntityType<*>,
                                    blockPos: BlockPos, state: BlockState
) : BlockEntityBase<T>(blockEntityType, blockPos, state), IHandled {
    abstract fun getInv(): IHandleStack<*>

    override fun load(tag: CompoundTag) {
        super.load(tag)
        this.getInv().deserializeTag(tag)
    }

    override fun saveAdditional(tag: CompoundTag) {
        tag.merge(this.getInv().serializeTag())
    }

    fun usedByPlayer(player: Player): Boolean {
        val pos = this.blockPos
        return player.distanceToSqr(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5) <= 64;
    }
}
