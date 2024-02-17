/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.common

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult
import team._0mods.aeternus.api.util.IntValue

interface BlockEvent {
    companion object {
        @JvmField val BREAK = EventFactory.createEventResult<Break>()
        @JvmField val PLACE = EventFactory.createEventResult<Place>()
        @JvmField val FALLING_LAND = EventFactory.createNoResult<FallingLand>()
    }

    fun interface Break {
        fun breakBlock(level: Level, pos: BlockPos, state: BlockState, player: ServerPlayer, xp: IntValue?): EventResult
    }

    fun interface Place {
        fun placeBlock(level: Level, pos: BlockPos, state: BlockState, placer: Entity?): EventResult
    }

    fun interface FallingLand {
        fun onLand(level: Level, pos: BlockPos, fallState: BlockState, landOn: BlockState, entity: FallingBlockEntity)
    }
}
