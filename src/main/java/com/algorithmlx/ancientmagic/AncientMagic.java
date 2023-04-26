package com.algorithmlx.ancientmagic;

import api.ancientmagic.AncientMagicTabs;
import api.ancientmagic.mod.Constant;
import com.algorithmlx.ancientmagic.init.Register;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constant.Key)
public class AncientMagic {
    public AncientMagic() {
        Constant.LOGGER.debug("Starting Mod!");

        MinecraftForge.EVENT_BUS.addListener(this::registerGroup);
        MinecraftForge.EVENT_BUS.addListener(AncientMagicTabs::registerTabs);

        Register.init();
    }

    private void registerGroup(CreativeModeTabEvent.BuildContents e) {
        if (e.getTab() == AncientMagicTabs.MAGIC_ITEMS) {
            for (var item : Constant.LIST_OF_ITEMS_TO_MAGIC) {
                e.accept(()-> item);
            }
        }
    }
}
