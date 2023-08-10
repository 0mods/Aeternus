package team.zeds.ancientmagic.forge.init;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import team.zeds.ancientmagic.forge.capability.PlayerMagicCapability;

public class AMCapability {
    public static final Capability<PlayerMagicCapability> PLAYER_MAGIC_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
}
