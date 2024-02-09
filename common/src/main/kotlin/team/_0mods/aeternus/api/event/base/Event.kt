package team._0mods.aeternus.api.event.base

interface Event<T> {
    val event: T

    fun register(listener: T)

    fun unregister(listener: T)

    fun isRegistered(listener: T): Boolean

    fun resetListeners()
}