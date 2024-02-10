package team._0mods.aeternus.fabric.service

import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.core.EventHandler
import team._0mods.aeternus.api.gui.event.ScreenAccess
import team._0mods.aeternus.fabric.api.event.EventFactoryImpl
import team._0mods.aeternus.fabric.api.event.EventHandlerImpl
import team._0mods.aeternus.service.core.IEventHelper

class FabricEventHelper: IEventHelper {
    override val screenAccess: ScreenAccess
        get() = TODO("Not yet implemented")

    override val eventHandler: EventHandler
        get() = EventHandlerImpl

    override val eventFactory: EventFactory
        get() = EventFactoryImpl
}