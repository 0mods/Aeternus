package team.zeds.ancientmagic.common.api.network

import net.minecraft.network.FriendlyByteBuf

interface IAMPacket<T> {
    fun encode(byteBuf: FriendlyByteBuf)
    fun <PACK: IAMPacket<*>> decode(byteBuf: FriendlyByteBuf): PACK
    fun handle(networkContext: T) {}
}