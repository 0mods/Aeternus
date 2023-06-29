package team.zeds.ancientmagic.network.s2c

import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import team.zeds.ancientmagic.client.packet.ClientPlayerMagicData
import team.zeds.ancientmagic.network.PacketBase
import java.util.function.Supplier

class PlayerMagicDataSyncS2CPacket: PacketBase {
    val data: Int

    constructor(magicData: Int) {
        this.data = magicData
    }

    constructor(byteBuf: FriendlyByteBuf) {
        this.data = byteBuf.readInt()
    }

    override fun encode(byteBuf: FriendlyByteBuf) {
        byteBuf.writeInt(this.data)
    }

    companion object {
        @JvmStatic
        fun decode(buf: FriendlyByteBuf): PlayerMagicDataSyncS2CPacket {
            return PlayerMagicDataSyncS2CPacket(buf)
        }
    }

    override fun handle(ctxSup: Supplier<NetworkEvent.Context>) {
        val context = ctxSup.get()
        context.enqueueWork { ClientPlayerMagicData.playerData = data }
    }
}