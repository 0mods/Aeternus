package api.ancientmagic.group;

import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Supplier;

public class AncientMagicTabs {
    public static CreativeModeTab MAGIC_ITEMS;
    public static CreativeModeTab MAGIC_BLOCKS;
    public static CreativeModeTab DECORATE_BLOCKS;

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register e) {
        MAGIC_ITEMS = e.registerCreativeModeTab(registerTabId("magic_items"),
                b ->
                        b.icon(() -> new ItemStack(Items.AIR))
                        .title(registerNames("magicItems"))
        );

        MAGIC_BLOCKS = e.registerCreativeModeTab(registerTabId("magic_blocks"),
                b ->
                        b.icon(supplierOfItemStack(new ItemStack(Items.AIR)))
                        .title(registerNames("magicBlocks"))
        );

        DECORATE_BLOCKS= e.registerCreativeModeTab(registerTabId("decorate_blocks"),
                b ->
                        b.icon(supplierOfItemStack(new ItemStack(Items.AIR)))
                        .title(registerNames("decorateBlocks"))
        );
    }

    private static Component registerNames(String tabName) {
        return Component.translatable(String.format("itemGroup.%s.%s", Constant.Key,tabName));
    }

    private static ResourceLocation registerTabId(String tabId) {
        return new ResourceLocation(Constant.Key, tabId);
    }

    private static Supplier<ItemStack> supplierOfItemStack(ItemStack stack) {
        return () -> stack;
    }
}
