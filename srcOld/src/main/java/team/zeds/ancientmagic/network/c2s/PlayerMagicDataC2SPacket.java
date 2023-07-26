package team.zeds.ancientmagic.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import team.zeds.ancientmagic.api.item.MagicItem;
import team.zeds.ancientmagic.api.mod.AMConstant;
import team.zeds.ancientmagic.init.registries.AMCapability;

import java.util.function.Supplier;

public class PlayerMagicDataC2SPacket {
    public PlayerMagicDataC2SPacket(FriendlyByteBuf byteBuf) {}
    public PlayerMagicDataC2SPacket() {}

    public void encode(FriendlyByteBuf byteBuf) {}

    public static PlayerMagicDataC2SPacket decode(FriendlyByteBuf byteBuf) {
        return new PlayerMagicDataC2SPacket(byteBuf);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        final var context = ctxSup.get();

        context.enqueueWork(()-> {
            var serverPlayer = context.getSender();
            var stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.getItem() instanceof MagicItem item) {
                serverPlayer.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap -> {
                    if (cap.getMagicLevel() >= item.getMagicType().asLevel()) item.setItemUse(true);
                    else {
                        AMConstant.LOGGER.debug("Player haven't required level for use item ({})", item);
                        item.setItemUse(false);
                    }
                });
            }
        });
    }
}
