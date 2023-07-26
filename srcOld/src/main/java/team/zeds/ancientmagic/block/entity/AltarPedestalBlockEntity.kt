package team.zeds.ancientmagic.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.api.block.InventoriedBlockEntity
import team.zeds.ancientmagic.api.handler.HandleStack
import team.zeds.ancientmagic.init.registries.AMRegister

class AltarPedestalBlockEntity(pos: BlockPos, state: BlockState) : InventoriedBlockEntity(
    AMRegister.ALTAR_PEDESTAL_BLOCK_ENTITY.get(), pos, state
) {
     private val inv: HandleStack

    init {
        inv = createHandler(::changeX)
    }

    override fun getInv(): HandleStack = inv

    fun createHandler(contextChange: Runnable): HandleStack = HandleStack.create(1, contextChange) { builder ->
        builder.setDefaultSlotLimit(1)
    }!!
}
