package team.zeromods.ancientmagic.network.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface BaseC2SPacketImpl {
    void toBytes(FriendlyByteBuf byteBuf);

    boolean handle(Supplier<NetworkEvent.Context> ctxSup);
}
