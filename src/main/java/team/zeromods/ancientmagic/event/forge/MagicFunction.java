package team.zeromods.ancientmagic.event.forge;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeromods.ancientmagic.init.AMTags;

public class MagicFunction {
    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (e.getItemStack().getItem() instanceof MagicItem item) {
            tooltip.add(Component.translatable("magicType.typeof", item.getMagicType().getTranslation()));
            if (item.maxMana(stack) != 0) {
                tooltip.add(item.getMagicType().getMagicTooltip("storage", item.getStorageMana(stack)));
            }
            var resource = ForgeRegistries.ITEMS.getKey(item);
            var namespace = resource.getNamespace();

            if (!namespace.equals(Constant.Key))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", Constant.Key), namespace));

            if (stack.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)
                    || stack.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST)) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", Constant.Key)));
            }
        }
    }
}
