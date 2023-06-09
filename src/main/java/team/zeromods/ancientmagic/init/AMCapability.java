package team.zeromods.ancientmagic.init;

import api.ancientmagic.mod.Constant;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import api.ancientmagic.unstandardable.MagicObjectCapability;
import team.zeromods.ancientmagic.capability.PlayerMagicCapability;

public class AMCapability {
    public static final Capability<PlayerMagicCapability> PLAYER_MAGIC_HANDLER =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static final Capability<MagicObjectCapability> MAGIC_OBJECT =
            MagicObjectCapability.Provider.MAGIC_OBJECT;

    public static final ResourceLocation
            PLAYER_MAGIC_HANDLER_ID = cl("player_magic"),
            MAGIC_OBJECT_ID = cl("magic_obj");

    private static ResourceLocation cl(String name) {
        return new ResourceLocation(Constant.Key, name);
    }
}
