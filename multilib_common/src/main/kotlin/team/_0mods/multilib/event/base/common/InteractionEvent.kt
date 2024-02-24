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
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import team._0mods.multilib.event.core.EventFactory
import team._0mods.multilib.event.result.EventResult
import team._0mods.multilib.event.result.EventResultHolder

interface InteractionEvent {
    companion object {
        @JvmField val LEFT_CLICK_BLOCK = EventFactory.createEventResult<team._0mods.multilib.event.base.common.InteractionEvent.LeftClickBlock>()
        @JvmField val RIGHT_CLICK_BLOCK = EventFactory.createEventResult<team._0mods.multilib.event.base.common.InteractionEvent.RightClickBlock>()
        @JvmField val RIGHT_CLICK_ITEM = EventFactory.createEventResultHolder<team._0mods.multilib.event.base.common.InteractionEvent.RightClickItem>()
        @JvmField val CLIENT_LEFT_CLICK_AIR = EventFactory.createNoResult<team._0mods.multilib.event.base.common.InteractionEvent.LeftClickEmpty>()
        @JvmField val CLIENT_RIGHT_CLICK_AIR = EventFactory.createNoResult<team._0mods.multilib.event.base.common.InteractionEvent.RightClickEmpty>()
        @JvmField val INTERACT_ENTITY = EventFactory.createEventResult<team._0mods.multilib.event.base.common.InteractionEvent.InteractEntity>()
        @JvmField val FARMLAND_TRAMPLE = EventFactory.createEventResult<team._0mods.multilib.event.base.common.InteractionEvent.FarmlandTrample>()
    }

    fun interface RightClickBlock {
        fun click(player: Player, hand: InteractionHand, pos: BlockPos, direction: Direction): EventResult
    }

    fun interface LeftClickBlock {
        fun click(player: Player, hand: InteractionHand, pos: BlockPos, direction: Direction): EventResult
    }

    fun interface RightClickItem {
        fun click(player: Player, hand: InteractionHand): EventResultHolder<ItemStack>
    }

    fun interface RightClickEmpty {
        fun click(player: Player, hand: InteractionHand)
    }

    fun interface LeftClickEmpty {
        fun click(player: Player, hand: InteractionHand)
    }

    fun interface InteractEntity {
        fun interact(player: Player, entity: Entity, hand: InteractionHand): EventResult
    }

    fun interface FarmlandTrample {
        fun trample(level: Level, pos: BlockPos, state: BlockState, distance: Float, entity: Entity): EventResult
    }
}
