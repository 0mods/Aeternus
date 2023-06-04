package team.zeromods.ancientmagic.network;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.mod.Constant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import team.zeromods.ancientmagic.init.AMCapability;
import team.zeromods.ancientmagic.network.base.BaseC2SPacket;

import java.util.Objects;
import java.util.function.Supplier;

public class PlayerMagicDataC2SPacket extends BaseC2SPacket {
    public PlayerMagicDataC2SPacket(FriendlyByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctxSup) {
        var context = ctxSup.get();

        context.enqueueWork(()-> {
            var serverPlayer = context.getSender();

            if (serverPlayer != null) {
                var serverLevel = serverPlayer.getLevel();
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

        return true;
    }
}
