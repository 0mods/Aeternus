package team.zeds.ancientmagic.common.api.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.common.api.block.settings.TickedBlockEntity

open class EntityBlockBase<E: BlockEntity>(
    val blockEntity: (BlockPos, BlockState) -> E,
    properties: Properties
): Block(properties), EntityBlock {
    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity? = this.blockEntity.invoke(p0, p1)

    override fun <T : BlockEntity> getTicker(
        `$$0`: Level,
        `$$1`: BlockState,
        `$$2`: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return BlockEntityTicker { level: Level, pos: BlockPos, state: BlockState, entity: T ->
            if (entity is TickedBlockEntity<*>) {
                if (level.isClientSide())
                    (entity as TickedBlockEntity<T>).tickOnClient(level, pos, state, entity)
                else (entity as TickedBlockEntity<T>).tickOnServer(level, pos, state, entity)
            }
        }
    }
}
