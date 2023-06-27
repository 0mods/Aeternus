package team.zeds.ancientmagic.network.s2c

import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import team.zeds.ancientmagic.client.ClientPlayerMagicData
import java.util.function.Supplier

class PlayerMagicDataSyncS2CPacket {
    val data: Int

    constructor(magicData: Int) {
        this.data = magicData
    }

    constructor(byteBuf: FriendlyByteBuf) {
        this.data = byteBuf.readInt()
    }

    fun encode(buf: FriendlyByteBuf) {
        buf.writeInt(this.data)
    }

    companion object {
        @JvmStatic
        fun decode(buf: FriendlyByteBuf): PlayerMagicDataSyncS2CPacket {
            return PlayerMagicDataSyncS2CPacket(buf)
        }
    }

    fun handle(ctxSup: Supplier<NetworkEvent.Context>) {
        val context = ctxSup.get()
        context.enqueueWork { ClientPlayerMagicData.setPlayerData(data) }
    }
}