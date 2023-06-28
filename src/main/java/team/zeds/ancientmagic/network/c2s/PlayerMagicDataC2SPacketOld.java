package team.zeds.ancientmagic.network.c2s;

import net.minecraft.network.chat.Component;
import team.zeds.ancientmagic.api.MagicItem;
import team.zeds.ancientmagic.api.mod.Constant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import team.zeds.ancientmagic.init.AMCapability;

import java.util.Objects;
import java.util.function.Supplier;

public class PlayerMagicDataC2SPacketOld {
    public PlayerMagicDataC2SPacketOld(FriendlyByteBuf byteBuf) {}

    public PlayerMagicDataC2SPacketOld() {}

    public void encode(FriendlyByteBuf byteBuf) {}

    public static PlayerMagicDataC2SPacketOld decode(FriendlyByteBuf byteBuf) {
        return new PlayerMagicDataC2SPacketOld(byteBuf);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        var context = ctxSup.get();

        context.enqueueWork(()-> {
            var serverPlayer = context.getSender();

            if (serverPlayer != null) {
                var stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);

                if (stack.getItem() instanceof MagicItem item) {

                    serverPlayer.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                        stack.getCapability(AMCapability.MAGIC_OBJECT).ifPresent(iCap -> {
                            if (cap.getMagicLevel() >= Objects.requireNonNull(iCap.getMagicType()).numerate())
                                item.setItemUse(true);
                            else {
                            /*if (serverLevel.isClientSide) {
                                serverPlayer.displayClientMessage(MagicType.getMagicMessage("notLevel",
                                                item.getMagicType().getTranslation(),
                                                MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation()),
                                        true);

                            } else*/ Constant.LOGGER.debug("Player haven't required level for use item");
                                item.setItemUse(false);
                            }

                        })
                    );
                }
            }
        });
    }
}
