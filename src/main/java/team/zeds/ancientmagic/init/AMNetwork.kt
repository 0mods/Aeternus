package team.zeds.ancientmagic.init

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.simple.SimpleChannel
import team.zeds.ancientmagic.api.mod.Constant
import team.zeds.ancientmagic.network.c2s.PlayerMagicDataC2SPacket
import team.zeds.ancientmagic.network.s2c.PlayerMagicDataSyncS2CPacket
import java.util.function.Supplier

object AMNetwork {
    private const val NTW_VER = "1"
    @JvmField var INSTANCE: SimpleChannel? = null

    private var packetIndex: Int = 0

    @JvmStatic
    private fun id(): Int = packetIndex++

    @JvmStatic @Synchronized
    fun init() {
        val network: SimpleChannel = NetworkRegistry.newSimpleChannel(
            ResourceLocation(Constant.KEY, "main"),
            { NTW_VER },
            { anObject: String -> NTW_VER == (anObject) },
            { anObject: String -> NTW_VER == (anObject) }
        )

        INSTANCE = network

        network.messageBuilder(PlayerMagicDataC2SPacket::class.java, id(), NetworkDirection.PLAY_TO_SERVER)
            .decoder { byteBuf: FriendlyByteBuf ->
                PlayerMagicDataC2SPacket.decode(
                    byteBuf
                )
            }
            .encoder { obj: PlayerMagicDataC2SPacket, byteBuf: FriendlyByteBuf ->
                obj.encode(
                    byteBuf
                )
            }
            .consumerMainThread { obj: PlayerMagicDataC2SPacket, ctxSup: Supplier<NetworkEvent.Context> ->
                obj.handle(
                    ctxSup
                )
            }
            .add()
        network.messageBuilder(
            PlayerMagicDataSyncS2CPacket::class.java,
            id(),
            NetworkDirection.PLAY_TO_CLIENT
        )
            .decoder { byteBuf: FriendlyByteBuf ->
                PlayerMagicDataSyncS2CPacket.decode(
                    byteBuf
                )
            }
            .encoder { obj: PlayerMagicDataSyncS2CPacket, byteBuf: FriendlyByteBuf ->
                obj.encode(
                    byteBuf
                )
            }
            .consumerMainThread { obj: PlayerMagicDataSyncS2CPacket, ctxSup: Supplier<NetworkEvent.Context> ->
                obj.handle(
                    ctxSup
                )
            }
            .add()
    }

    @JvmStatic
    fun <MSG> sendToServer(message: MSG) {
        INSTANCE?.sendToServer(message)
    }
    @JvmStatic
    fun <MSG> sendToPlayer(message: MSG, player: ServerPlayer?) {
        INSTANCE?.send(PacketDistributor.PLAYER.with { player }, message)
    }
}