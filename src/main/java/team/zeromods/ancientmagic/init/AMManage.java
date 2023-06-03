package team.zeromods.ancientmagic.init;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
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
import team.zeromods.ancientmagic.event.forge.MagicData;

public class AMManage {
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
        Constant.LOGGER.debug("Initializing forge events");
        bus.addListener(AMManage::modCommon);
        bus.addListener(AMCommands::registerCommands);
        bus.addListener(event -> {
            MagicData.playerEvent(genericEvent(event));
            MagicData.registerCapability(genericEvent(event));
            MagicData.attachCapability(genericEvent(event));
            MagicData.playerClone(genericEvent(event));
        });
    }

    private static void modEventsInitialize(IEventBus bus) {
        Constant.LOGGER.debug("Initializing mod events");
        CompactInitializer.init(bus);
        bus.addListener(AncientMagicTabs::registerTabs);
    }

    private static void modCommon(final FMLCommonSetupEvent e) {
        PROXY.init();
    }

    @Mod.EventBusSubscriber(modid = Constant.Key, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientInit implements ProxyBase {
        @SubscribeEvent
        public static void client(final FMLClientSetupEvent e) {
            FORGE_BUS.addListener(MagicData::tooltipEvent);
        }

        @Override
        public void init() {
            Constant.LOGGER.debug("Initializing client dist");
        }
    }

    @Mod.EventBusSubscriber(modid = Constant.Key, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
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

    private static <T extends Event, A extends Event> T genericEvent(A e) {
        return (T) e;
    }
}
