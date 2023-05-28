package team.zeromods.ancientmagic;

import api.ancientmagic.group.AncientMagicTabs;
import api.ancientmagic.mod.Constant;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.config.CommonConfiguration;
import team.zeromods.ancientmagic.events.MagicFunction;
import team.zeromods.ancientmagic.init.AMRegister;
import team.zeromods.ancientmagic.init.AMTags;

@Mod(Constant.Key)
public class AncientMagic {
    public AncientMagic() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.SPEC.build());

        Constant.LOGGER.debug("Starting Mod!");

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        modBus.addListener(AncientMagicTabs::registerTabs);
        forgeBus.addListener(MagicFunction::tooltipEvent);

        AMTags.init();
        AMRegister.init();
        CompactInitializer.init();
    }
}
