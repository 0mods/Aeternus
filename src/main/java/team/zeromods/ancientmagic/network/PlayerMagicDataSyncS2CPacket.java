package team.zeromods.ancientmagic.network;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import team.zeromods.ancientmagic.client.ClientPlayerMagicData;
import team.zeromods.ancientmagic.init.AMCapability;
import team.zeromods.ancientmagic.network.base.BaseC2SPacket;

import java.util.function.Supplier;

public class PlayerMagicDataSyncS2CPacket extends BaseC2SPacket {
    private final int data;

    public PlayerMagicDataSyncS2CPacket(int magicData) {
        super();
        this.data = magicData;
    }

    public PlayerMagicDataSyncS2CPacket(FriendlyByteBuf byteBuf) {
        super(byteBuf);
        this.data = byteBuf.readInt();
    }

    @Override
    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctxSup) {
        var context = ctxSup.get();
        context.enqueueWork(()-> ClientPlayerMagicData.setPlayerData(this.data));

        return true;
    }
}
