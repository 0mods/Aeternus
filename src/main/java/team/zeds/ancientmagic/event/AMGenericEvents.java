package team.zeds.ancientmagic.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import team.zeds.ancientmagic.api.block.MagicBlockEntity;
import team.zeds.ancientmagic.api.mod.AMConstant;
import team.zeds.ancientmagic.capability.PlayerMagicCapability;
import team.zeds.ancientmagic.init.registries.AMCapability;

import static team.zeds.ancientmagic.api.mod.AMConstant.reloc;

public final class AMGenericEvents {
    public static void attachCapabilityToPlayer(final AttachCapabilitiesEvent<Player> event) {
        if (!event.getObject().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).isPresent())
            event.addCapability(reloc("player_magic"), new PlayerMagicCapability.Provider());
    }

    public static void attachCapabilityToBlockEntity(final AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof MagicBlockEntity) {
            if (!event.getObject().getCapability(AMCapability.BLOCK_MAGIC_CAPABILITY).isPresent())
                event.addCapability(reloc("block_mana"), AMConstant.BLOCK_MANA_CAPABILITY);
        }
    }
}
