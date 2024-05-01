/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.fluid

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.FluidTags
import net.minecraft.world.item.Item
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

abstract class EtheriumFluid: FlowingFluid() {
    override fun getBucket(): Item = AeternusRegsitry.etheriumBucket

    override fun canBeReplacedWith(
        state: FluidState,
        level: BlockGetter,
        pos: BlockPos,
        fluid: Fluid,
        direction: Direction
    ): Boolean = direction == Direction.DOWN && !fluid.`is`(FluidTags.WATER)

    override fun getTickDelay(level: LevelReader): Int  = 5

    override fun getExplosionResistance(): Float = 100F

    override fun createLegacyBlock(state: FluidState): BlockState = AeternusRegsitry.etheriumFluidBlock.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state))

    override fun getFlowing(): Fluid = AeternusRegsitry.etheriumFlowing

    override fun getSource(): Fluid = AeternusRegsitry.etheriumFluid

    override fun canConvertToSource(level: Level): Boolean = level.gameRules.getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION)

    override fun beforeDestroyingBlock(level: LevelAccessor, pos: BlockPos, state: BlockState) {
        val be = if (state.hasBlockEntity()) level.getBlockEntity(pos) else null
        Block.dropResources(state, level, pos, be)
    }

    override fun getSlopeFindDistance(level: LevelReader): Int = 4

    override fun getDropOff(level: LevelReader): Int = 1

    override fun isSame(fluid: Fluid): Boolean = fluid == this.source || fluid == this.flowing

    class Source: EtheriumFluid() {
        override fun isSource(state: FluidState): Boolean = true

        override fun getAmount(state: FluidState): Int = 8
    }

    class Flowing: EtheriumFluid() {
        override fun createFluidStateDefinition(builder: StateDefinition.Builder<Fluid, FluidState>) {
            super.createFluidStateDefinition(builder)
            builder.add(LEVEL)
        }

        override fun isSource(state: FluidState): Boolean = false

        override fun getAmount(state: FluidState): Int = state.getValue(LEVEL)
    }
}
