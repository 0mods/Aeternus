package team.zeds.ancientmagic.api.network

import net.minecraft.network.FriendlyByteBuf

interface IAMPacket<T> {
    fun encode(byteBuf: FriendlyByteBuf)
    fun <PACK: IAMPacket<*>> decode(byteBuf: FriendlyByteBuf): PACK
    fun handle(networkContext: T) {}
}