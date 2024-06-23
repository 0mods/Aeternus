package team._0mods.aeternus.platformredirect.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import team._0mods.aeternus.api.util.mcemulate.MCPlayer;

@Mixin(Player.class)
public class PlayerMixin implements  MCPlayer {
}
