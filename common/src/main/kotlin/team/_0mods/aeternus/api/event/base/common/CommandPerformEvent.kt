package team._0mods.aeternus.api.event.base.common

import com.mojang.brigadier.ParseResults
import net.minecraft.commands.CommandSourceStack
import team._0mods.aeternus.api.event.core.EventActor
import team._0mods.aeternus.api.event.core.EventFactory

class CommandPerformEvent(var results: ParseResults<CommandSourceStack>, var throwable: Throwable?) {
    companion object {
        @JvmField val EVENT = EventFactory.createEventActorLoop<EventActor<CommandPerformEvent>>()
    }
}