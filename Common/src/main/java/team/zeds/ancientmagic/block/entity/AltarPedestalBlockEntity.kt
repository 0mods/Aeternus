package team.zeds.ancientmagic.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.api.block.ContainerBlockEntity
import team.zeds.ancientmagic.api.helper.IHandleStack
import team.zeds.ancientmagic.platform.AMServices

class AltarPedestalBlockEntity(pos: BlockPos, state: BlockState): ContainerBlockEntity(
    AMServices.PLATFORM.getAltarPedestalBlockEntityType(),
    pos,
    state
) {
    private val inv: IHandleStack

    init {
        this.inv = createHandler(this::changeX)
    }

    override fun getInv(): IHandleStack {
        TODO("Not yet implemented")
    }

    override fun createHandler(contextChange: Runnable): IHandleStack =
        AMServices.PLATFORM.getIHandleStackForAltarPedestalBlockEntity(contextChange)
}
