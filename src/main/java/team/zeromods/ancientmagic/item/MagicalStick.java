package team.zeromods.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.IMagicType;
import api.ancientmagic.magic.MagicType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;

public class MagicalStick extends MagicItem {
    public MagicalStick() {
        super(new Properties());
    }

    @Override
    public IMagicType getMagicType() {
        return MagicType.LOW_MAGIC;
    }

    @Override
    public int maxMana() {
        return 1500;
    }

    @Override
    public void magicState(Level level, Player player, InteractionHand hand) {

    }
}
