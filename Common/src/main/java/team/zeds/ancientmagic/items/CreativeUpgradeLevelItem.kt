package team.zeds.ancientmagic.items

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.magic.MagicType
import team.zeds.ancientmagic.api.magic.MagicTypes
import team.zeds.ancientmagic.client.sync.ClientPlayerMagicData
import team.zeds.ancientmagic.platform.AMServices

class CreativeUpgradeLevelItem: MagicItem(of().setMagicType(MagicTypes.LOW_MAGIC).setMagicType(MagicTypes.ADMIN)) {
    override fun useMT(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val cap = AMServices.PLATFORM.getPlayerMagic(player)!!
        val currentLevel = cap.getMagicLevel()
        if (!player.isShiftKeyDown) {
            if (currentLevel < 4) {
                cap.addLevel(1)
                AMServices.PLATFORM.getIAMNetwork().sendToServer(AMServices.PLATFORM.getC2SPlayerPacket())
                if (level.isClientSide)
                    player.displayClientMessage(
                        MagicType.getMagicMessage(
                            "admin.levelAdded",
                            MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() + 1).getTranslation()
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
            }
        } else {
            if (currentLevel <= 4 && currentLevel != 0) {
                cap.subLevel(1)
                AMServices.PLATFORM.getIAMNetwork().sendToServer(AMServices.PLATFORM.getC2SPlayerPacket())
                if (level.isClientSide()) player.displayClientMessage(
                    MagicType.getMagicMessage(
                        "admin.levelAdded",
                        MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData() - 1).getTranslation()
                    ),
                    true
                )
            } else if (currentLevel == 0) {
                if (level.isClientSide()) player.displayClientMessage(
                    MagicType.getMagicMessage(
                        "admin.levelMin",
                        MagicTypes.getByNumeration(currentLevel).getTranslation()
                    ), true
                )
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand))
    }
}