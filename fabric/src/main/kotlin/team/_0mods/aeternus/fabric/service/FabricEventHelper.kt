package team._0mods.aeternus.fabric.service

import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.core.EventHandler
import team._0mods.aeternus.fabric.api.event.EventFactoryImpl
import team._0mods.aeternus.fabric.api.event.EventHandlerImpl
import team._0mods.aeternus.service.core.IEventHelper

class FabricEventHelper: IEventHelper {
    override fun eventHandlerImpl(): EventHandler = EventHandlerImpl

    override fun eventFactoryImpl(): EventFactory = EventFactoryImpl
}