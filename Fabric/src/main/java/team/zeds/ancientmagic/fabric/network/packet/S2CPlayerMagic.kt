package team.zeds.ancientmagic.fabric.network.packet

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import team.zeds.ancientmagic.common.api.network.IAMPacket
import team.zeds.ancientmagic.fabric.capability.PlayerMagicCapability
import team.zeds.ancientmagic.fabric.network.AMNetwork
import team.zeds.ancientmagic.fabric.util.EntityDataHolder

class S2CPlayerMagic: IAMPacket<PacketSender> {
    private var byteBuf: FriendlyByteBuf? = null
    override fun receive(
        client: Minecraft,
        listener: ClientPacketListener,
        buf: FriendlyByteBuf,
        sender: PacketSender
    ) {
        this.byteBuf = buf
        val player = client.player
        PlayerMagicCapability.getInstance(player).save((player as EntityDataHolder))
    }

    override fun getId(): ResourceLocation = AMNetwork.S2C_PLAYER_MAGIC_ID

    override fun getBuf(): FriendlyByteBuf? = this.byteBuf
}
