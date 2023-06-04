package team.zeromods.ancientmagic.network.base;

import net.minecraft.network.FriendlyByteBuf;

public abstract class BaseC2SPacket implements BaseC2SPacketImpl {
    public BaseC2SPacket() {}

    public BaseC2SPacket(FriendlyByteBuf byteBuf) {}
}
