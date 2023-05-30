package team.zeromods.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.IMagicType;
import api.ancientmagic.magic.MagicType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicalStick extends MagicItem {
    public MagicalStick() {
        super(MagicBuilder.get().setProperties(new Properties()));
    }
}
