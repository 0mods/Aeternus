package team._0mods.aeternus.api.event.base.common

import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import team._0mods.aeternus.api.event.core.EventFactory

interface LifecycleEvent {
    companion object {
        @JvmField val SERVER_ABOUT_TO_START = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STARTING = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STARTED = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STOPPING = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STOPPED = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_LEVEL_LOAD = EventFactory.createNoResult<ServerLevelState>()
        @JvmField val SERVER_LEVEL_UNLOAD = EventFactory.createNoResult<ServerLevelState>()
        @JvmField val SERVER_LEVEL_SAVE = EventFactory.createNoResult<ServerLevelState>()
        @JvmField val SETUP = EventFactory.createNoResult<Runnable>()
    }

    fun interface ServerState: InstanceState<MinecraftServer>

    fun interface ServerLevelState: LevelState<ServerLevel>

    fun interface InstanceState<T> {
        fun onChanged(instance: T)
    }

    fun interface LevelState<T: Level> {
        fun onChanged(instance: T)
    }
}