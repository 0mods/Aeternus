package team.zeds.ancientmagic.common.api.network

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ServerPacketListener
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

interface IAMPacket<T> {
    // FORGE PACKET
    fun encode(byteBuf: FriendlyByteBuf) {}
    fun decode(byteBuf: FriendlyByteBuf): IAMPacket<T>? = null
    fun handle(networkContext: T) {}
    fun getId(): ResourceLocation
    fun getBuf(): FriendlyByteBuf?
    // FABRIC PACKET
    fun receive(client: Minecraft, listener: ClientPacketListener, buf: FriendlyByteBuf, sender: T) {}
    fun receive(server: MinecraftServer, player: ServerPlayer, handler: ServerPacketListener, buf: FriendlyByteBuf, sender: T) {}
}