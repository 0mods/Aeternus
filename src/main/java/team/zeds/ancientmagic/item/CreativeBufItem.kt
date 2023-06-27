package team.zeds.ancientmagic.item

import net.minecraft.commands.Commands
import net.minecraft.world.item.ItemStack
import team.zeds.ancientmagic.api.MagicItem
import team.zeds.ancientmagic.api.atomic.KAtomicUse
import team.zeds.ancientmagic.api.magic.*
import team.zeds.ancientmagic.client.ClientPlayerMagicData
import team.zeds.ancientmagic.init.AMCapability
import team.zeds.ancientmagic.init.AMNetwork
import team.zeds.ancientmagic.network.c2s.PlayerMagicDataC2SPacket

class CreativeBufItem: MagicItem(MagicBuilder.get().setMagicType(MagicTypes.LOW_MAGIC).setMagicType(MagicTypes.ADMIN)) {
    override fun use(use: KAtomicUse<ItemStack>) {
        val player = use.player
        val level = use.level
        val stack = use.stack

        if (!(player.hasPermissions(Commands.LEVEL_ADMINS) || player.isCreative))
            use.setConsume(stack)
        else {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent { cap ->
                val currentLevel = cap.magicLevel
                if (!player.isShiftKeyDown) {
                    if (currentLevel < 4) {
                        cap.addLevel(1)
                        AMNetwork.sendToServer(PlayerMagicDataC2SPacket())
                        if (level.isClientSide)
                            player.displayClientMessage(
                                MagicType.getMagicMessage(
                                    "admin.levelAdded",
                                    MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() + 1).getTranslation()
                                ),
                                true
                            )
                        use.setSuccess(stack)
                    } else if (currentLevel == 4) {
                        if (level.isClientSide()) player.displayClientMessage(
                            MagicType.getMagicMessage(
                                "admin.levelMax",
                                MagicTypes.getByNumeration(currentLevel).getTranslation()
                            ), true
                        )
                        use.setFail(stack)
                    }
                } else {
                    if (currentLevel <= 4 && currentLevel != 0) {
                        cap.subLevel(1)
                        AMNetwork.sendToServer(PlayerMagicDataC2SPacket())
                        if (level.isClientSide()) player.displayClientMessage(
                            MagicType.getMagicMessage(
                                "admin.levelAdded",
                                MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() - 1).getTranslation()
                            ),
                            true
                        )
                        use.setSuccess(stack)
                    } else if (currentLevel == 0) {
                        if (level.isClientSide()) player.displayClientMessage(
                            MagicType.getMagicMessage(
                                "admin.levelMin",
                                MagicTypes.getByNumeration(currentLevel).getTranslation()
                            ), true
                        )
                        use.setSuccess(stack)
                    }
                }
            }
        }
    }
}