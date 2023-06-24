package team.zeromods.ancientmagic.item;

import team.zeromods.ancientmagic.api.atomic.KAtomicUse;
import team.zeromods.ancientmagic.api.MagicItem;
import team.zeromods.ancientmagic.api.magic.MagicType;
import team.zeromods.ancientmagic.api.magic.MagicTypes;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import team.zeromods.ancientmagic.client.ClientPlayerMagicData;
import team.zeromods.ancientmagic.init.AMCapability;
import team.zeromods.ancientmagic.init.AMNetwork;
import team.zeromods.ancientmagic.network.PlayerMagicDataC2SPacket;

import static team.zeromods.ancientmagic.api.magic.MagicTypes.*;

public class CreativeBufItem extends MagicItem {
    public CreativeBufItem() {
        super(MagicBuilder.get().setMagicType(MagicTypes.LOW_MAGIC).setMagicSubtype(MagicTypes.ADMIN));
    }

//    @Override
//    public void onActive(Level level, Player player, InteractionHand hand) {
//        if (player.hasPermissions(Commands.LEVEL_ADMINS) || player.isCreative()) {
//
//            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap -> {
//                var currentLevel = cap.getMagicLevel();
//                if (!player.isShiftKeyDown()) {
//                    if (currentLevel < 4) {
//                        cap.addLevel(1);
//                        AMNetwork.sendToServer(new PlayerMagicDataC2SPacket());
//                        if (level.isClientSide())
//                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
//                                    MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() + 1).getTranslation()),
//                                    true);
//                    } else if (currentLevel == 4) {
//                        if (level.isClientSide())
//                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMax",
//                                    MagicTypes.getByNumeration(currentLevel).getTranslation()), true);
//                    }
//                } else {
//                    if (currentLevel <= 4 && currentLevel != 0) {
//                        cap.subLevel(1);
//                        AMNetwork.sendToServer(new PlayerMagicDataC2SPacket());
//                        if (level.isClientSide()) {
//                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
//                                        MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() - 1).getTranslation()),
//                                    true);
//                        }
//                    } else if (currentLevel == 0) {
//                        if (level.isClientSide())
//                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMin",
//                                    MagicTypes.getByNumeration(currentLevel).getTranslation()), true);
//                    }
//                }
//            });
//        }
//    }

    @Override
    public void use(KAtomicUse<ItemStack> atomicUse) {
        var player = atomicUse.getPlayer();
        var level = atomicUse.getLevel();
        var stack = atomicUse.getStack();

        if (!(player.hasPermissions(Commands.LEVEL_ADMINS) || player.isCreative()))
            atomicUse.setConsume(stack);
        else {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap-> {
                var currentLevel = cap.getMagicLevel();
                if (!player.isShiftKeyDown()) {
                    if (currentLevel < 4) {
                        cap.addLevel(1);
                        AMNetwork.sendToServer(new PlayerMagicDataC2SPacket());
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
                                    getByNumeration(ClientPlayerMagicData.getPlayerData() + 1).getTranslation()),
                                    true);
                        atomicUse.setSuccess(stack);
                    } else if (currentLevel == 4) {
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMax",
                                    getByNumeration(currentLevel).getTranslation()), true);
                        atomicUse.setFail(stack);
                    }
                } else {
                    if (currentLevel <= 4 && currentLevel != 0) {
                        cap.subLevel(1);
                        AMNetwork.sendToServer(new PlayerMagicDataC2SPacket());
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
                                        getByNumeration(ClientPlayerMagicData.getPlayerData() - 1).getTranslation()),
                                    true);
                        atomicUse.setSuccess(stack);
                    } else if (currentLevel == 0) {
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMin",
                                    getByNumeration(currentLevel).getTranslation()), true);
                        atomicUse.setSuccess(stack);
                    }
                }
            });
        }
    }
}
