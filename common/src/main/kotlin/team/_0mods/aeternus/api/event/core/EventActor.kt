package team._0mods.aeternus.api.event.core

import team._0mods.aeternus.api.event.result.EventResult

interface EventActor<T> {
    fun act(t: T): EventResult
}