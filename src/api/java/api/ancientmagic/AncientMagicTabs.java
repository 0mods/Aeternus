package api.ancientmagic;

import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AncientMagicTabs {
    public static CreativeModeTab MAGIC_ITEMS;

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register e) {
        MAGIC_ITEMS = e.registerCreativeModeTab(new ResourceLocation(Constant.Key, ""),
                b ->
                        b.icon(() -> new ItemStack(Items.AIR))
                        .title(registerNames("magicItems"))
        );
    }

    private static Component registerNames(String tabName) {
        return Component.translatable(String.format("itemGroup.%s.%s", Constant.Key,tabName));
    }
}
