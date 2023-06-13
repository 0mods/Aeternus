package team.zeromods.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.atomic.AtomicUse;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicTypes;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import team.zeromods.ancientmagic.client.ClientPlayerMagicData;
import team.zeromods.ancientmagic.init.AMCapability;
import team.zeromods.ancientmagic.init.AMNetwork;
import team.zeromods.ancientmagic.network.PlayerMagicDataC2SPacket;

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
    public void use(AtomicUse<ItemStack> atomicUse) {
        var player = atomicUse.getPlayer();
        var level = atomicUse.getLevel();
        var stack = atomicUse.getStack();

        if (!(player.hasPermissions(Commands.LEVEL_ADMINS) || player.isCreative()))
            atomicUse.setFailHolder(stack);
        else {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap-> {
                var currentLevel = cap.getMagicLevel();
                if (!player.isShiftKeyDown()) {
                    if (currentLevel < 4) {
                        cap.addLevel(1);
                        AMNetwork.sendToServer(new PlayerMagicDataC2SPacket());
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
                                    MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() + 1).getTranslation()),
                                    true);
                        atomicUse.setSuccessHolder(stack);
                    } else if (currentLevel == 4) {
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMax",
                                    MagicTypes.getByNumeration(currentLevel).getTranslation()), true);
                        atomicUse.setFailHolder(stack);
                    }
                } else {
                    if (currentLevel <= 4 && currentLevel != 0) {
                        cap.subLevel(1);
                        AMNetwork.sendToServer(new PlayerMagicDataC2SPacket());
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
                                        MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() - 1).getTranslation()),
                                    true);
                        atomicUse.setSuccessHolder(stack);
                    } else if (currentLevel == 0) {
                        if (level.isClientSide())
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMin",
                                    MagicTypes.getByNumeration(currentLevel).getTranslation()), true);
                        atomicUse.setSuccessHolder(stack);
                    }
                }
            });
        }
    }
}
