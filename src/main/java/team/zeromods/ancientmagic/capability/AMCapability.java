package team.zeromods.ancientmagic.capability;

import api.ancientmagic.mod.Constant;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class AMCapability {
    public static final Capability<PlayerMagicTypeHandler> PLAYER_MAGIC_HANDLER =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation PLAYER_MAGIC_HANDLER_ID =
            new ResourceLocation(Constant.Key, "player_magic");
}
