package team._0mods.aeternus.api.event.base.common

import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult

interface ChatEvent {
    companion object {
        @JvmField val DECORATE = EventFactory.createNoResult<Decorate>()
        @JvmField val RECEIVED = EventFactory.createEventResult<Received>()
    }

    fun interface Decorate {
        fun decode(player: ServerPlayer, component: ChatComponent)
    }

    fun interface Received {
        fun received(player: ServerPlayer?, component: Component): EventResult
    }

    interface ChatComponent {
        var component: Component
    }
}
