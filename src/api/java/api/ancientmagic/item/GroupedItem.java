package api.ancientmagic.item;

import net.minecraft.world.item.Item;

import java.util.List;

public abstract class GroupedItem extends Item {
    public GroupedItem(Properties p_41383_) {
        super(p_41383_);
        this.toGroup().add(this);
    }

    public abstract List<Item> toGroup();
}
