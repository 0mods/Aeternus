package team._0mods.aeternus.service.core

import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.core.EventHandler

interface IEventHelper {
    fun eventHandlerImpl(): EventHandler

    fun eventFactoryImpl(): EventFactory
}