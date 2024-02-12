package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import team._0mods.aeternus.api.event.base.common.LifecycleEvent
import team._0mods.aeternus.api.event.core.EventFactory

interface ClientLifecycleEvent {
    companion object {
        @JvmField val CLIENT_SETUP = EventFactory.createNoResult<ClientState>()
        @JvmField val CLIENT_STARTED = EventFactory.createNoResult<ClientState>()
        @JvmField val CLIENT_STOPPING = EventFactory.createNoResult<ClientState>()
        @JvmField val CLIENT_LEVEL_LOAD = EventFactory.createNoResult<ClientLevelState>()
    }

    interface ClientState: LifecycleEvent.InstanceState<Minecraft>

    interface ClientLevelState: LifecycleEvent.LevelState<ClientLevel>
}