package team._0mods.aeternus.service.core

import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.core.EventHandler
import team._0mods.aeternus.api.gui.event.ScreenAccess

interface IEventHelper {
    val screenAccess: ScreenAccess

    val eventHandler: EventHandler

    val eventFactory: EventFactory
}