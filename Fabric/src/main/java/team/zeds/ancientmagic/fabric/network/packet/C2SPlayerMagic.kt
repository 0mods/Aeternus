package team.zeds.ancientmagic.fabric.network.packet

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ServerPacketListener
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import team.zeds.ancientmagic.common.api.item.MagicItem
import team.zeds.ancientmagic.common.api.magic.MagicTypes
import team.zeds.ancientmagic.common.api.network.IAMPacket
import team.zeds.ancientmagic.fabric.capability.PlayerMagicCapability
import team.zeds.ancientmagic.fabric.network.AMNetwork
import team.zeds.ancientmagic.fabric.util.EntityDataHolder

class C2SPlayerMagic(private val buf: FriendlyByteBuf): IAMPacket<PacketSender> {
    override fun receive(
        server: MinecraftServer,
        player: ServerPlayer,
        handler: ServerPacketListener,
        buf: FriendlyByteBuf,
        sender: PacketSender
    ) {
        val stack = player.getItemInHand(InteractionHand.MAIN_HAND)
        val cap = PlayerMagicCapability.getInstance(player)
        val item = stack.item
        cap.save(player as EntityDataHolder)

        if (item is MagicItem) {
            if (cap.getMagicLevel() >= item.getMagicType().asLevel()) item.itemUse = false
            else {
                if (player.level().isClientSide) player.displayClientMessage(
                    Component.translatable("magic.ancientmagic.notLevel",
                    item.getMagicType().getTranslation(),
                    MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation()), true)
                item.itemUse = false
            }
        }
    }

    override fun getId(): ResourceLocation = AMNetwork.C2S_PLAYER_MAGIC_ID

    override fun getBuf(): FriendlyByteBuf = this.buf
}