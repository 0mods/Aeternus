package team._0mods.aeternus.api.event.base.common

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.CommandSelection
import team._0mods.aeternus.api.event.core.EventFactory

fun interface CommandRegistrationEvent {
    companion object {
        @JvmField val EVENT = EventFactory.createNoResult<CommandRegistrationEvent>()
    }

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>, registry: CommandBuildContext, selection: CommandSelection)
}