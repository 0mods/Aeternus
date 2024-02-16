package team._0mods.aeternus.api.forge

import java.util.Optional
import java.util.function.Consumer

interface EventBusHelper<T> {
    fun registerModEvent(modId: String, bus: T)

    fun whenAvailable(modId: String, bus: Consumer<T>)

    fun getModEventBus(modId: String): Optional<T>
}