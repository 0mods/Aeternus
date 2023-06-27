package team.zeds.ancientmagic.init;

import team.zeds.ancientmagic.api.mod.Constant;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AMTags {
    public static TagKey<Item> UNCONSUMABLE_TELEPORTATION_CATALYST;
    public static TagKey<Item> CONSUMABLE_TELEPORTATION_CATALYST;

    public static void init() {
        UNCONSUMABLE_TELEPORTATION_CATALYST = createTag("unbreakable_tp");
        CONSUMABLE_TELEPORTATION_CATALYST = createTag("consume_tp");
    }

    protected static TagKey<Item> createTag(String tagName) {
        var tag = TagKey.create(Registries.ITEM, new ResourceLocation(Constant.KEY, tagName));

        Constant.LOGGER.debug("{} has been registered or initialized!", tag.location());
        return tag;
    }
}
