package team._0mods.aeternus.service.core

import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.core.EventHandler
import team._0mods.aeternus.api.gui.screen.ScreenHooks

interface EventHelper {
    val screenHooks: ScreenHooks

    val eventHandler: EventHandler

    val eventFactory: EventFactory
}