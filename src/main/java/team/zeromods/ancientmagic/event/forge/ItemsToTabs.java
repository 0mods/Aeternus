package team.zeromods.ancientmagic.event.forge;

import api.ancientmagic.group.AncientMagicTabs;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.RegistryObject;
import team.zeromods.ancientmagic.init.AMRegister;

public class ItemsToTabs {
    public static void addItems(CreativeModeTabEvent.BuildContents e) {
        if (e.getTab() == AncientMagicTabs.ANCIENT_MAGIC_TAB) {
            AMRegister.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(i -> e.accept(()-> i));
        }
    }
}
