package api.ancientmagic.group;

import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;

import java.util.function.Supplier;

public class AncientMagicTabs {
    public static CreativeModeTab ANCIENT_MAGIC_TAB;

    public static void registerTabs(CreativeModeTabEvent.Register e) {
        ANCIENT_MAGIC_TAB = e.registerCreativeModeTab(registerTabId("ancientmagic"),
                b ->
                        b.icon(supConvert(Items.AIR))
                        .title(registerNames("magicItems"))
        );
    }

    private static Component registerNames(String tabName) {
        return Component.translatable(String.format("itemGroup.%s.%s", Constant.Key,tabName));
    }

    private static ResourceLocation registerTabId(String tabId) {
        return new ResourceLocation(Constant.Key, tabId);
    }

    private static Supplier<ItemStack> supConvert(Item item) {
        return () -> new ItemStack(item);
    }
}
