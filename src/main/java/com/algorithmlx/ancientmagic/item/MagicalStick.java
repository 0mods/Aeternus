package com.algorithmlx.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.MagicType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MagicalStick extends MagicItem {
    public MagicalStick() {
        super(new Properties());
    }

    @Override
    public MagicType getMagicType() {
        return MagicType.ATTACK;
    }

    @Override
    public int maxMana() {
        return 1500;
    }

    @Override
    public void stateFunction(Level level, Player player) {

    }
}
