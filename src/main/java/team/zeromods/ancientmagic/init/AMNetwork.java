package team.zeromods.ancientmagic.init;

import api.ancientmagic.mod.Constant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import team.zeromods.ancientmagic.network.PlayerMagicDataC2SPacket;
import team.zeromods.ancientmagic.network.PlayerMagicDataSyncS2CPacket;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AMNetwork {
    private static final String NTW_VER = "1";
    public final static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(//AMNetwork:18
            new ResourceLocation(Constant.Key, "main"),
            ()-> NTW_VER,
            NTW_VER::equals,
            NTW_VER::equals
    );

    private static int packetIndex = 0;

    public static void init() {
        register(PlayerMagicDataC2SPacket.class, PlayerMagicDataC2SPacket::encode, PlayerMagicDataC2SPacket::decode,
                PlayerMagicDataC2SPacket::handle);
        register(PlayerMagicDataSyncS2CPacket.class, PlayerMagicDataSyncS2CPacket::encode,
                PlayerMagicDataSyncS2CPacket::decode, PlayerMagicDataSyncS2CPacket::handle);
    }

    private static <T> void register(Class<T> messType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                                     BiConsumer<T, Supplier<NetworkEvent.Context>> messConsumer) {
        INSTANCE.registerMessage(++packetIndex, messType, encoder, decoder, messConsumer);
    }
}
