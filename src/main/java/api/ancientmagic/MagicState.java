package api.ancientmagic;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface MagicState {
    MagicType getMagicType();
    int maxMana();
    void stateFunction(Level level, Player player);
}
