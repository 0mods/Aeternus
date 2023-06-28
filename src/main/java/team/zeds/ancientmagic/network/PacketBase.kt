package team.zeds.ancientmagic.network

import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

interface PacketBase {
    fun encode(byteBuf: FriendlyByteBuf)
    fun handle(ctxSup: Supplier<NetworkEvent.Context>)
}