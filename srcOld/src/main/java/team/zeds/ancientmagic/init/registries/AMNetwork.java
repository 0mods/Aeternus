package team.zeds.ancientmagic.init.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import team.zeds.ancientmagic.api.mod.AMConstant;
import team.zeds.ancientmagic.network.c2s.PlayerMagicDataC2SPacket;
import team.zeds.ancientmagic.network.s2c.PlayerMagicDataSyncS2CPacket;

public class AMNetwork {
    private static final String NTW_VER = "1";

    public static SimpleChannel INSTANCE;

    private static int packetIndex = 0;

    private static int id() {
        return packetIndex++;
    }

    public static synchronized void init() {
        SimpleChannel network = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(AMConstant.KEY, "main"),
                () -> NTW_VER,
                NTW_VER::equals,
                NTW_VER::equals
        );

        INSTANCE = network;

        network.messageBuilder(PlayerMagicDataC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerMagicDataC2SPacket::decode)
                .encoder(PlayerMagicDataC2SPacket::encode)
                .consumerMainThread(PlayerMagicDataC2SPacket::handle)
                .add();

        network.messageBuilder(PlayerMagicDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerMagicDataSyncS2CPacket::decode)
                .encoder(PlayerMagicDataSyncS2CPacket::encode)
                .consumerMainThread(PlayerMagicDataSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
    }
}