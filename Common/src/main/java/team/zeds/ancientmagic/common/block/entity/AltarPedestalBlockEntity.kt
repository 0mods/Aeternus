package team.zeds.ancientmagic.common.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.common.api.block.ContainerBlockEntity
import team.zeds.ancientmagic.common.api.helper.IHandleStack
import team.zeds.ancientmagic.common.platform.AMServices

class AltarPedestalBlockEntity(pos: BlockPos, state: BlockState): ContainerBlockEntity(
    AMServices.PLATFORM.getIAMRegistryEntry().getAltarPedestalBlockEntityType(),
    pos,
    state
) {
    private val inv: IHandleStack

    init {
        this.inv = createHandler(this::changeX)
    }

    override fun getInv(): IHandleStack = this.inv

    override fun createHandler(contextChange: Runnable): IHandleStack =
        AMServices.PLATFORM.getIHandleStackForAltarPedestalBlockEntity(contextChange)
}
