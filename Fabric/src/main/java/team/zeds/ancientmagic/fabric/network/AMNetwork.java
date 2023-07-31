package team.zeds.ancientmagic.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import team.zeds.ancientmagic.common.api.network.IAMNetwork;
import team.zeds.ancientmagic.common.api.network.IAMPacket;
import team.zeds.ancientmagic.fabric.network.packet.C2SPlayerMagic;
import team.zeds.ancientmagic.fabric.network.packet.S2CPlayerMagic;

import static team.zeds.ancientmagic.common.AMConstant.reloc;

public class AMNetwork implements IAMNetwork {
    public static C2SPlayerMagic C2S_PLAYER_MAGIC = new C2SPlayerMagic();
    public static S2CPlayerMagic S2C_PLAYER_MAGIC = new S2CPlayerMagic();

    public static final ResourceLocation C2S_PLAYER_MAGIC_ID = reloc("player_magic_sync");
    public static final ResourceLocation S2C_PLAYER_MAGIC_ID = reloc("player_magic_server_sync");

    public static void registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(C2S_PLAYER_MAGIC_ID, C2S_PLAYER_MAGIC::receive);
    }
    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(S2C_PLAYER_MAGIC_ID, S2C_PLAYER_MAGIC::receive);
    }

    @Override
    public <MSG> void sendToServer(MSG message) {
        var packet = (IAMPacket<?>) message;
        ClientPlayNetworking.send(packet.getId(), packet.getBuf());
    }

    @Override
    public <MSG> void sendToPlayer(MSG message, @NotNull ServerPlayer player) {
        var packet = (IAMPacket<?>) message;
        ServerPlayNetworking.send(player, packet.getId(), packet.getBuf());
    }
}
