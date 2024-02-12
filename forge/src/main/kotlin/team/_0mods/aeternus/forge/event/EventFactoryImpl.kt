package team._0mods.aeternus.forge.event

import net.minecraftforge.common.MinecraftForge
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.core.EventActor
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult.Companion.pass
import team._0mods.aeternus.api.event.result.EventResult.Companion.result
import java.util.function.Consumer

internal object EventFactoryImpl: EventFactory() {
    @ApiStatus.Internal
    override fun <T> attachToForge(evt: Event<Consumer<T>>): Event<Consumer<T>> {
        evt.register {
            if (it !is net.minecraftforge.eventbus.api.Event)
                throw ClassCastException("${it!!::class.java} is not an instance of forge Event!")
            MinecraftForge.EVENT_BUS.post(it)
        }
        return evt
    }

    @ApiStatus.Internal
    override fun <T> attachToForgeEventActor(evt: Event<EventActor<T>>): Event<EventActor<T>> {
        evt.register {
            if (it !is net.minecraftforge.eventbus.api.Event)
                throw ClassCastException("${it!!::class.java} is not an instance of forge Event!")
            if (!it.isCancelable) throw ClassCastException("${it!!::class.java} is not cancellable Event!")
            return@register pass()
        }

        return evt
    }

    @ApiStatus.Internal
    override fun <T> attachToForgeEventActorCancellable(evt: Event<EventActor<T>>): Event<EventActor<T>> {
        evt.register {
            if (it !is net.minecraftforge.eventbus.api.Event)
                throw ClassCastException("${it!!::class.java} is not an instance of forge Event!")
            if (!it.isCancelable) throw ClassCastException("${it!!::class.java} is not cancellable Event!")
            if (MinecraftForge.EVENT_BUS.post(it))
                return@register result(false)
            return@register pass()
        }

        return evt
    }
}