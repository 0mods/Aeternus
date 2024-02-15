package team._0mods.aeternus.api.event.base.client

import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.core.EventFactory.Companion.createEventResult
import team._0mods.aeternus.api.event.result.EventResult
import team._0mods.aeternus.api.event.result.EventResultHolder

interface ClientChatEvent {
    companion object {
        @JvmField
        val SEND: Event<ClientChatEvent> = createEventResult()

        @JvmField
        val RECEIVED: Event<Received> = createEventResult()
    }

    fun interface Send {
        fun send(message: String?, component: Component?): EventResult?
    }

    fun interface Received {
        fun process(type: ChatType.Bound?, message: Component?): EventResultHolder<Component?>?
    }
}
