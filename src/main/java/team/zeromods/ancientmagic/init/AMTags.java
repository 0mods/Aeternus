package team.zeromods.ancientmagic.init;

import api.ancientmagic.mod.Constant;
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
        return TagKey.create(Registries.ITEM, new ResourceLocation(Constant.Key,tagName));
    }
}
