package team.zeromods.ancientmagic.init;

import api.ancientmagic.mod.Constant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import team.zeromods.ancientmagic.network.PlayerMagicDataC2SPacket;
import team.zeromods.ancientmagic.network.PlayerMagicDataSyncS2CPacket;

public class AMNetwork {
    private static SimpleChannel INSTANCE;

    private static int packetIndex = 0;

    private static int index() {
        return packetIndex;
    }

    public static void init() {
        SimpleChannel network = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Constant.Key, "message"))
                .networkProtocolVersion(()-> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel(); //finalizing

        INSTANCE = network;

        network.messageBuilder(PlayerMagicDataC2SPacket.class, index(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerMagicDataC2SPacket::new)
                .encoder(PlayerMagicDataC2SPacket::toBytes)
                .consumerMainThread(PlayerMagicDataC2SPacket::handle)
                .add(); //finalizing

        network.messageBuilder(PlayerMagicDataSyncS2CPacket.class, index(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerMagicDataSyncS2CPacket::new)
                .encoder(PlayerMagicDataSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerMagicDataSyncS2CPacket::handle)
                .add(); //finalizing
    }

    public static <T> void sendToServer(T message) {
        INSTANCE.sendToServer(message);
    }

    public static <T> void sendToPlayer(T message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
    }
}
