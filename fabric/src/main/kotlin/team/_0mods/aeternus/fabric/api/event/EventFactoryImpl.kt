package team._0mods.aeternus.fabric.api.event

import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.core.EventActor
import team._0mods.aeternus.api.event.core.EventFactory
import java.util.function.Consumer

internal object EventFactoryImpl: EventFactory() {
    override fun <T> attachToForge(evt: Event<Consumer<T>>): Event<Consumer<T>> = evt

    override fun <T> attachToForgeEventActor(evt: Event<EventActor<T>>): Event<EventActor<T>> = evt

    override fun <T> attachToForgeEventActorCancellable(evt: Event<EventActor<T>>): Event<EventActor<T>> = evt
}