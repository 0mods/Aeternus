package team.zeromods.ancientmagic.events;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.mod.Constant;
import team.zeromods.ancientmagic.init.AMTags;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class MagicFunction {
    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (stack.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)
                || stack.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST)) {
            tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", Constant.Key)));
        }

        if (e.getItemStack().getItem() instanceof MagicItem item) {
            tooltip.add(Component.translatable("magicType.typeof", item.getMagicType().getTranslation()));
            tooltip.add(Component.translatable(String.format("item.%s.magic.storage", Constant.Key), item.getStoragedMana()));

            var resource = ForgeRegistries.ITEMS.getKey(item);
            var namespace = resource.getNamespace();

            if (!namespace.equals(Constant.Key))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", Constant.Key), namespace));
        }
    }
}
