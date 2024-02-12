package team._0mods.aeternus.api.event.core

import team._0mods.aeternus.api.event.result.EventResult

fun interface EventActor<T> {
    fun act(t: T): EventResult
}