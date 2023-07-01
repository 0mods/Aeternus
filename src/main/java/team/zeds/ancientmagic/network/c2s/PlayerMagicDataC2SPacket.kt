package team.zeds.ancientmagic.network.c2s

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraftforge.network.NetworkEvent
import team.zeds.ancientmagic.api.item.MagicBlockItem
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.mod.Constant
import team.zeds.ancientmagic.capability.PlayerMagicCapability
import team.zeds.ancientmagic.init.registries.AMCapability
import team.zeds.ancientmagic.network.PacketBase
import java.util.function.Supplier

class PlayerMagicDataC2SPacket: PacketBase {
    constructor(byteBuf: FriendlyByteBuf)
    constructor()

    override fun encode(byteBuf: FriendlyByteBuf) {}

    companion object {
        @JvmStatic
        fun decode(byteBuf: FriendlyByteBuf): PlayerMagicDataC2SPacket = PlayerMagicDataC2SPacket(byteBuf)
    }

    override fun handle(ctxSup: Supplier<NetworkEvent.Context>) {
        val context = ctxSup.get()

        context.enqueueWork {
            val serverPlayer: ServerPlayer = context.sender!!

            val stack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND)

            if (stack.item is MagicItem) {
                val item = stack.item as MagicItem
                serverPlayer.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent { cap: PlayerMagicCapability ->
                    if (cap.getMagicLevel() >= item.getMagicType().numerate())
                        item.setItemUse(true)
                    else {
                        Constant.LOGGER.debug("Player haven't required level for use item")
                        item.setItemUse(false)
                    }
                }
            }

            if (stack.item is MagicBlockItem) {
                val item = stack.item as MagicBlockItem
                serverPlayer.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent { cap: PlayerMagicCapability ->
                    if (cap.getMagicLevel() >= item.getMagicType().numerate())
                        item.setItemUse(true)
                    else {
                        Constant.LOGGER.debug("Player haven't required level for use item")
                        item.setItemUse(false)
                    }
                }
            }
        }
    }
}