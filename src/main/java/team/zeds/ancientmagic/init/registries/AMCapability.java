package team.zeds.ancientmagic.init.registries;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import team.zeds.ancientmagic.capability.BlockManaCapability;
import team.zeds.ancientmagic.capability.PlayerMagicCapability;

public class AMCapability {
    public static final Capability<PlayerMagicCapability> PLAYER_MAGIC_HANDLER = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<BlockManaCapability> BLOCK_MAGIC_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

}
