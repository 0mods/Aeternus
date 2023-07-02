package team.zeds.ancientmagic.item

import net.minecraft.commands.Commands
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.magic.*
import team.zeds.ancientmagic.client.packet.ClientPlayerMagicData
import team.zeds.ancientmagic.init.registries.AMCapability
import team.zeds.ancientmagic.init.registries.AMNetwork
import team.zeds.ancientmagic.network.c2s.PlayerMagicDataC2SPacket

class CreativeBufItem: MagicItem(this.callBuilder().setMagicType(MagicTypes.LOW_MAGIC).setMagicSubtype(MagicTypes.ADMIN)) {
    override fun useMT(level: Level, player: Player, hand: InteractionHand) {
        if (!(player.hasPermissions(Commands.LEVEL_ADMINS) || player.isCreative))
            return
        else {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent { cap ->
                val currentLevel = cap.getMagicLevel()
                if (!player.isShiftKeyDown) {
                    if (currentLevel < 4) {
                        cap.addLevel(1)
                        AMNetwork.sendToServer(PlayerMagicDataC2SPacket())
                        if (level.isClientSide)
                            player.displayClientMessage(
                                MagicType.getMagicMessage(
                                    "admin.levelAdded",
                                    MagicTypes.getByNumeration(ClientPlayerMagicData.playerData.plus(1)).getTranslation()
                                ),
                                true
                            )

                    } else if (currentLevel == 4) {
                        if (level.isClientSide()) player.displayClientMessage(
                            MagicType.getMagicMessage(
                                "admin.levelMax",
                                MagicTypes.getByNumeration(currentLevel).getTranslation()
                            ), true
                        )
                        return@ifPresent
                    }
                } else {
                    if (currentLevel <= 4 && currentLevel != 0) {
                        cap.subLevel(1)
                        AMNetwork.sendToServer(PlayerMagicDataC2SPacket())
                        if (level.isClientSide()) player.displayClientMessage(
                            MagicType.getMagicMessage(
                                "admin.levelAdded",
                                MagicTypes.getByNumeration(ClientPlayerMagicData.playerData.minus(1)).getTranslation()
                            ),
                            true
                        )
                        return@ifPresent
                    } else if (currentLevel == 0) {
                        if (level.isClientSide()) player.displayClientMessage(
                            MagicType.getMagicMessage(
                                "admin.levelMin",
                                MagicTypes.getByNumeration(currentLevel).getTranslation()
                            ), true
                        )
                        return@ifPresent
                    }
                }
            }
        }
    }
}