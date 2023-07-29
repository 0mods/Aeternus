package team.zeds.ancientmagic.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import team.zeds.ancientmagic.common.api.network.IAMNetwork;

public class AMNetwork implements IAMNetwork {
    @Override
    public <MSG> void sendToServer(MSG message) {
        ClientPlayNetworking.send((FabricPacket) message);
    }

    @Override
    public <MSG> void sendToPlayer(MSG message, @NotNull ServerPlayer player) {
        ServerPlayNetworking.send(player, (FabricPacket) message);
    }
}
