package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.multiplayer.ClientLevel as MCClLevel
import team._0mods.aeternus.api.event.core.EventFactory

interface ClientTickEvent<T> {
    companion object {
        @JvmField val CLIENT_PRE = EventFactory.createNoResult<Client>()
        @JvmField val CLIENT_POST = EventFactory.createNoResult<Client>()

        @JvmField val LEVEL_PRE = EventFactory.createNoResult<ClientLevel>()
        @JvmField val LEVEL_POST = EventFactory.createNoResult<ClientLevel>()
    }

    fun tick(inst: T)

    interface Client: ClientTickEvent<Minecraft>

    interface ClintLevel: ClientTickEvent<MCClLevel>
}