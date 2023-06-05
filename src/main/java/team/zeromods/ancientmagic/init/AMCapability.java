package team.zeromods.ancientmagic.init;

import api.ancientmagic.mod.Constant;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import team.zeromods.ancientmagic.capability.PlayerMagicCapability;

public class AMCapability {
    public static final Capability<PlayerMagicCapability> PLAYER_MAGIC_HANDLER =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation PLAYER_MAGIC_HANDLER_ID =
            new ResourceLocation(Constant.Key, "player_magic");
}
