package team.zeromods.ancientmagic.event.mod;

import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import team.zeromods.ancientmagic.init.AMRegister;

import java.util.function.Supplier;

public class AncientMagicTabs {
    public static CreativeModeTab ANCIENT_MAGIC_TAB;

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register e) {
        ANCIENT_MAGIC_TAB = e.registerCreativeModeTab(registerTabId("ancientmagic"),
                b ->
                        b.icon(supConvert(AMRegister.RETRACE_CRYSTAL.get()))
                        .title(registerNames("tab"))
                        .displayItems((flags, output) -> AMRegister.ITEMS.getEntries().stream()
                                .map(RegistryObject::get).forEach(item -> output.accept(()-> item)))
        );
    }

    private static Component registerNames(String tabName) {
        return Component.translatable(String.format("itemTab.%s.%s", Constant.Key, tabName));
    }

    private static Supplier<ItemStack> supConvert(Item item) {
        return () -> new ItemStack(item);
    }

    private static ResourceLocation registerTabId(String tabId) {
        return new ResourceLocation(Constant.Key, tabId);
    }
}
