package team.zeds.ancientmagic.init.registries;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import team.zeds.ancientmagic.capability.PlayerMagicCapability;

public class AMCapability {
    public static final Capability<PlayerMagicCapability> PLAYER_MAGIC_HANDLER = CapabilityManager.get(new CapabilityToken<>() {});
}
