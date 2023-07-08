package team.zeds.ancientmagic.init;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import team.zeds.ancientmagic.api.mod.Constant;
import team.zeds.ancientmagic.event.AMMagicSetup;
import team.zeds.ancientmagic.init.config.*;
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
import team.zeds.ancientmagic.compact.CompactInitializer;
import team.zeds.ancientmagic.init.registries.AMCommands;
import team.zeds.ancientmagic.init.registries.AMNetwork;
import team.zeds.ancientmagic.init.registries.AMRegister;
import team.zeds.ancientmagic.init.registries.AMTags;

public class AMManage {
    protected static IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS;
    protected static IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    private static final ProxyBase PROXY = DistExecutor.safeRunForDist(()-> ClientInit::new, ()-> ServerInit::new);
    public static AMCommon COMMON_CONFIG;

    public static void init() {
        AMCommon common = new AMCommon();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, common.SPEC.build());
        COMMON_CONFIG = common;

        modEventsInitialize(MOD_BUS);
        forgeEventsInitialize(FORGE_BUS);

        AMTags.init();
        AMRegister.init();
    }

    private static void forgeEventsInitialize(IEventBus bus) {
        Constant.LOGGER.debug("Initializing forge events");
        bus.addListener(AMManage::modCommon);
        bus.addListener(AMCommands::registerCommands);
        bus.addListener(AMMagicSetup::playerClone);
        bus.addListener(AMMagicSetup::playerTick);
        bus.addGenericListener(Object.class, AMMagicSetup::attachCapability);
        bus.addListener(AMMagicSetup::playerConnectToWorld);
    }

    private static void modEventsInitialize(IEventBus bus) {
        Constant.LOGGER.debug("Initializing mod events");
        CompactInitializer.init(bus);
        bus.addListener(AMManage::modCommon);
        bus.addListener(AMMagicSetup::registerCapability);
    }

    private static void modCommon(final FMLCommonSetupEvent e) {
        PROXY.init();
        AMNetwork.init();
    }

    @Mod.EventBusSubscriber(modid = Constant.KEY, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientInit implements ProxyBase {
        @SubscribeEvent
        public static void client(final FMLClientSetupEvent e) {
            FORGE_BUS.addListener(AMMagicSetup::tooltipEvent);
        }

        @Override
        public void init() {
            Constant.LOGGER.debug("Initializing client dist");
        }
    }

    @Mod.EventBusSubscriber(modid = Constant.KEY, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
    public static class ServerInit implements ProxyBase {
        @SubscribeEvent
        public static void server(FMLDedicatedServerSetupEvent e) {}

        @Override
        public void init() {
            Constant.LOGGER.debug("Initializing client dist");
        }
    }

    @FunctionalInterface
    private interface ProxyBase { void init(); }
}
