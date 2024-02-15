package team._0mods.aeternus.forge.service

import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.core.EventHandler
import team._0mods.aeternus.api.gui.screen.ScreenHooks
import team._0mods.aeternus.forge.event.EventFactoryImpl
import team._0mods.aeternus.service.core.EventHelper

class ForgeEventHelper: EventHelper {
    override val screenHooks: ScreenHooks
        get() = TODO("Not yet implemented")

    override val eventHandler: EventHandler
        get() = TODO("Not yet implemented")

    override val eventFactory: EventFactory
        get() = EventFactoryImpl
}