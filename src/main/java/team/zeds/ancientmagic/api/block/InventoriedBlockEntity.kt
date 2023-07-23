package team.zeds.ancientmagic.api.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.IItemHandler
import team.zeds.ancientmagic.api.handler.HandleStack

abstract class InventoriedBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState)
    : BlockEntityBase(type, pos, state) {
    val cap: LazyOptional<IItemHandler> = LazyOptional.of(::getInv)

    abstract fun getInv(): HandleStack

    override fun load(tag: CompoundTag) {
        super.load(tag)
        this.getInv().deserializeNBT(tag)
    }

    override fun saveAdditional(tag: CompoundTag) {
        tag.merge(this.getInv().serializeNBT())
    }

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!this.isRemoved && cap == ForgeCapabilities.ITEM_HANDLER) return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, this.cap)
        return super.getCapability(cap, side)
    }

    fun usedByPlayer(player: Player): Boolean {
        val pos = this.blockPos
        return player.distanceToSqr(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5) <= 64;
    }
}