/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package team._0mods.multilib.event.base.common

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import team._0mods.multilib.event.core.EventFactory
import team._0mods.multilib.event.result.EventResult
import team._0mods.multilib.util.IntValue

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
