package api.ancientmagic.group;

import net.minecraft.world.level.ItemLike;

import java.util.List;

public interface GroupInitializer {
    List<ItemLike> toGroup();
}
