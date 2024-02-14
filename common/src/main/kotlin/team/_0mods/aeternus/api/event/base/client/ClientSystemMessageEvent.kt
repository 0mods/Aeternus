package team._0mods.aeternus.api.event.base.client

import net.minecraft.network.chat.Component
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResultHolder

interface ClientSystemMessageEvent {
    companion object {
        @JvmField val RECEIVED = EventFactory.createEventActorLoop<Received>()
    }

    interface Received {
        fun process(message: Component): EventResultHolder<Component>
    }
}