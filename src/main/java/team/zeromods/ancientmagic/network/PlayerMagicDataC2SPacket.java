package team.zeromods.ancientmagic.network;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.mod.Constant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import team.zeromods.ancientmagic.init.AMCapability;

import java.util.Objects;
import java.util.function.Supplier;

public class PlayerMagicDataC2SPacket {
    public PlayerMagicDataC2SPacket(FriendlyByteBuf byteBuf) {
    }

    public PlayerMagicDataC2SPacket() {

    }

    public void encode(FriendlyByteBuf byteBuf) {}

    public static PlayerMagicDataC2SPacket decode(FriendlyByteBuf byteBuf) {
        return new PlayerMagicDataC2SPacket(byteBuf);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        var context = ctxSup.get();

        context.enqueueWork(()-> {
            var serverPlayer = context.getSender();

            if (serverPlayer != null) {
                var stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);

                if (stack.getItem() instanceof MagicItem item) {
                    serverPlayer.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap -> {
                        if (cap.getMagicLevel() >= Objects.requireNonNull(item.getMagicType()).numerate())
                            item.canUseItem = true;
                        else {
                            /*if (serverLevel.isClientSide) {
                                serverPlayer.displayClientMessage(MagicType.getMagicMessage("notLevel",
                                                item.getMagicType().getTranslation(),
                                                MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation()),
                                        true);

                            } else*/ Constant.LOGGER.debug("Player haven't required level for use item");
                            item.canUseItem = false;
                        }
                    });
                }
            }
        });
    }
}
