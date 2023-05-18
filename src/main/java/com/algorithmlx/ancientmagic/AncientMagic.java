package com.algorithmlx.ancientmagic;

import api.ancientmagic.group.AncientMagicTabs;
import api.ancientmagic.mod.Constant;
import com.algorithmlx.ancientmagic.compact.CompactInitalizer;
import com.algorithmlx.ancientmagic.config.CommonConfiguration;
import com.algorithmlx.ancientmagic.init.Register;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Constant.Key)
public class AncientMagic {
    public AncientMagic() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.SPEC.build());

        Constant.LOGGER.debug("Starting Mod!");

        MinecraftForge.EVENT_BUS.addListener(this::registerGroup);
        MinecraftForge.EVENT_BUS.addListener(AncientMagicTabs::registerTabs);

        Register.init();
        CompactInitalizer.start();
    }

    private void registerGroup(CreativeModeTabEvent.BuildContents e) {
        if (e.getTab() == AncientMagicTabs.MAGIC_ITEMS)
            for (var item : Constant.LIST_OF_ITEMS_TO_MAGIC) e.accept(() -> item);

        if (e.getTab() == AncientMagicTabs.MAGIC_BLOCKS)
            for (var item : Constant.LIST_OF_BLOCK_TO_MAGIC) e.accept(() -> item);

        if (e.getTab() == AncientMagicTabs.DECORATE_BLOCKS)
            for (var item : Constant.LIST_OF_BLOCK_TO_DECORATE) e.accept(() -> item);
    }
}
