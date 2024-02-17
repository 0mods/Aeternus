/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.BlockHitResult
import team._0mods.aeternus.api.block.blockentity.IBlockEntity
import team._0mods.aeternus.api.block.blockentity.IMenued
import team._0mods.aeternus.api.block.blockentity.IStorable
import team._0mods.aeternus.api.block.blockentity.ITickBlockEntity

abstract class EntityBlockBase(private val blockEntity: (BlockPos, BlockState) -> BlockEntity, properties: Properties): BaseEntityBlock(properties) {
    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity = blockEntity.invoke(p0, p1)

    @Suppress(
            "DEPRECATION",
            "UNCHECKED_CAST"
    )
    override fun <T : BlockEntity?> getTicker(
            level: Level,
            state: BlockState,
            type: BlockEntityType<T>
    ): BlockEntityTicker<T>? = BlockEntityTicker { lvl, blockPos, blockState, entity ->
        if (entity is ITickBlockEntity<*>) {
            if (lvl.isClientSide())
                (entity as ITickBlockEntity<T>).tickOnClient(lvl, blockPos, blockState, entity)
            else (entity as ITickBlockEntity<T>).tickOnServer(lvl, blockPos, blockState, entity)
        }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        val be = level.getBlockEntity(pos)
        if (be is IBlockEntity<*>)
            be.onPlace(level, state, oldState, isMoving)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        val be = level.getBlockEntity(pos)
        if (be is IBlockEntity<*>)
            be.onRemove(level, state, oldState, isMoving)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        val be = level.getBlockEntity(pos)
        if (be is IBlockEntity<*>)
            be.onPlacedBy(level, state, placer, stack)
    }

    override fun playerDestroy(level: Level, player: Player, pos: BlockPos, state: BlockState, be: BlockEntity?, stack: ItemStack) {
        if (be is IStorable) {
            val stack1 = be.fromStorageToStack(ItemStack(this))
            Block.popResource(level, pos, stack1)
            player.awardStat(Stats.BLOCK_MINED.get(this))
            player.causeFoodExhaustion(0.005F)
        } else {
            super.playerDestroy(level, player, pos, state, be, stack)
        }
    }

    @Suppress(
            "OVERRIDE_DEPRECATION",
            "DEPRECATION"
    )
    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hitResult: BlockHitResult): InteractionResult {
        val be = level.getBlockEntity(pos)
        if (be is IMenued) {
            val prov = object: MenuProvider {
                override fun createMenu(p0: Int, p1: Inventory, p2: Player): AbstractContainerMenu? =
                        be.getContainer(p0, p1, p2, be)

                override fun getDisplayName(): Component = this@EntityBlockBase.name
            }

            val container = prov.createMenu(0, player.inventory, player)
            if (container != null) {
                if (player is ServerPlayer) player.openMenu(prov)

                return InteractionResult.SUCCESS
            }
        }

        return super.use(state, level, pos, player, hand, hitResult)
    }

    @Suppress(
            "OVERRIDE_DEPRECATION",
            "DEPRECATION"
    )
    override fun updateShape(state: BlockState, facing: Direction, facingState: BlockState, levelAccessor: LevelAccessor, currentPos: BlockPos, facingPos: BlockPos): BlockState {
        if (this is SimpleWaterloggedBlock && state.getValue(WATERLOGGED))
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor))
        if (!state.canSurvive(levelAccessor, currentPos)) {
            val be = levelAccessor.getBlockEntity(currentPos)
            if (!levelAccessor.isClientSide && be is IStorable) {
                val isBE = be as IStorable
                val stack = isBE.fromStorageToStack(ItemStack(this))
                popResource(levelAccessor as Level, currentPos, stack)
                levelAccessor.destroyBlock(currentPos, false)
            }
        }

        return super.updateShape(state, facing, facingState, levelAccessor, currentPos, facingPos)
    }
}
