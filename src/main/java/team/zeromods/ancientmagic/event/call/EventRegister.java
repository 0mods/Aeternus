package team.zeromods.ancientmagic.event.call;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import team.zeromods.ancientmagic.config.AMCommon;
import team.zeromods.ancientmagic.event.forge.AncientMagicTabs;
import api.ancientmagic.mod.Constant;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.event.forge.MagicFunction;
import team.zeromods.ancientmagic.init.AMRegister;
import team.zeromods.ancientmagic.init.AMTags;

public class EventRegister {
    protected static IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS;
    protected static IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    private static final ProxyBase PROXY = DistExecutor.safeRunForDist(()-> ClientInit::new, ()-> ServerInit::new);

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AMCommon.SPEC.build());

        forgeEventsInitialize(FORGE_BUS);
        modEventsInitialize(MOD_BUS);

        AMTags.init();
        AMRegister.init();
    }

    private static void forgeEventsInitialize(IEventBus bus) {

    }

    private static void modEventsInitialize(IEventBus bus) {
        CompactInitializer.init(bus);
        bus.addListener(EventRegister::modCommon);
        bus.addListener(AncientMagicTabs::registerTabs);
    }

    private static void modCommon(final FMLCommonSetupEvent e) {
        PROXY.init();
    }

    @Mod.EventBusSubscriber(modid = Constant.Key, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientInit implements ProxyBase {
        @SubscribeEvent
        public static void client(final FMLClientSetupEvent e) {
            FORGE_BUS.addListener(MagicFunction::tooltipEvent);
        }

        @Override
        public void init() {}
    }

    @Mod.EventBusSubscriber(modid = Constant.Key, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
    public static class ServerInit implements ProxyBase {
        @SubscribeEvent
        public static void server(FMLDedicatedServerSetupEvent e) {}

        @Override
        public void init() {}
    }

    private interface ProxyBase { void init(); }
}
