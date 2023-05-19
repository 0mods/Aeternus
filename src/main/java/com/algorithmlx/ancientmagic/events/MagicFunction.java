package com.algorithmlx.ancientmagic.events;

import api.ancientmagic.mod.Constant;
import com.algorithmlx.ancientmagic.init.AMTags;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MagicFunction {
    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent e) {
        if (e.getItemStack().is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)
                || e.getItemStack().is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST)) {
            e.getToolTip().add(Component.translatable(String.format("compact.%s.waystones.tpItem", Constant.Key)));
        }
    }
}
