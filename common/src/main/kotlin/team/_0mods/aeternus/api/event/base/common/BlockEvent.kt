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