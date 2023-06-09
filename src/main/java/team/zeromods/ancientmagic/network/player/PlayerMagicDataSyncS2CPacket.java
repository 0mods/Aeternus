package team.zeromods.ancientmagic.network.player;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import team.zeromods.ancientmagic.client.ClientPlayerMagicData;

import java.util.function.Supplier;

public class PlayerMagicDataSyncS2CPacket {
    private final int data;

    public PlayerMagicDataSyncS2CPacket(int magicData) {
        this.data = magicData;
    }

    public PlayerMagicDataSyncS2CPacket(FriendlyByteBuf byteBuf) {
        this.data = byteBuf.readInt();
    }

    public void encode(FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(this.data);
    }

    public static PlayerMagicDataSyncS2CPacket decode(FriendlyByteBuf byteBuf) {
        return new PlayerMagicDataSyncS2CPacket(byteBuf);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        var context = ctxSup.get();
        context.enqueueWork(()-> ClientPlayerMagicData.setPlayerData(this.data));
    }
}
