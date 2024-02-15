package team._0mods.aeternus.api.event.base.common

import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult

interface ExplosionEvent {
    companion object {
        @JvmField val PRE = EventFactory.createEventResult<Pre>()
        @JvmField val DETONATE = EventFactory.createNoResult<Detonate>()
    }

    fun interface Pre {
        fun explode(level: Level, explosion: Explosion): EventResult
    }

    fun interface Detonate {
        fun explode(level: Level, explosion: Explosion, affectedEntities: List<Entity>)
    }
}
