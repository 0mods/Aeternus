package team.zeromods.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicTypes;
import net.minecraft.commands.Commands;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import team.zeromods.ancientmagic.init.AMCapability;
import team.zeromods.ancientmagic.init.AMNetwork;
import team.zeromods.ancientmagic.network.PlayerMagicDataC2SPacket;

public class CreativeBufItem extends MagicItem {
    public CreativeBufItem() {
        super(MagicBuilder.get().setMagicType(MagicTypes.LOW_MAGIC).setMagicSubtype(MagicTypes.ADMIN));
    }

    @Override
    public void onActive(Level level, Player player, InteractionHand hand) {
        if (player.hasPermissions(Commands.LEVEL_ADMINS) || player.isCreative()) {

            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap -> {
                var currentLevel = cap.getMagicLevel();

                    if (!player.isShiftKeyDown()) {
                        if (currentLevel < 4) {
                            var addition = currentLevel + 1;
                            cap.addLevel(1);
                            AMNetwork.INSTANCE.sendToServer(new PlayerMagicDataC2SPacket());
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
                                    MagicTypes.getByNumeration(addition).getTranslation()), true);
                        } else if (currentLevel == 4) {
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMax",
                                    MagicTypes.getByNumeration(currentLevel).getTranslation()), true);
                        }
                    } else {
                        if (currentLevel <= 4 && currentLevel != 0) {
                            var minus = currentLevel - 1;
                            cap.subLevel(1);
                            AMNetwork.INSTANCE.sendToServer(new PlayerMagicDataC2SPacket());
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelAdded",
                                    MagicTypes.getByNumeration(minus).getTranslation()), true);
                        } else if (currentLevel == 0) {
                            player.displayClientMessage(MagicType.getMagicMessage("admin.levelMin",
                                    MagicTypes.getByNumeration(currentLevel).getTranslation()), true);
                        }
                    }
            });
        }
    }
}
