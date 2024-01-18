package team._0mods.aeternus.neo.api

import net.neoforged.bus.api.Event
import net.neoforged.fml.event.IModBusEvent
import thedarkcolour.kotlinforforge.neoforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

inline fun <reified T: Event> forgeEvent(noinline evt: (T) -> Unit) {
    FORGE_BUS.addListener<T> { evt.invoke(it) }
}

inline fun <reified T> modEvent(noinline evt: (T) -> Unit) where T: Event, T: IModBusEvent {
    MOD_BUS.addListener<T> { evt.invoke(it) }
}
