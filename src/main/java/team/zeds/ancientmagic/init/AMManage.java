package team.zeds.ancientmagic.init;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import team.zeds.ancientmagic.api.mod.Constant;
import team.zeds.ancientmagic.event.*;
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
    public static AMCommon COMMON_CONFIG;

    public static void init() {
        AMCommon common = AMCommon.getInstance();
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
        bus.register(AMForgeEvents.class);

        bus.addGenericListener(Player.class, AMGenericEvents::attachCapabilityToPlayer);
        bus.addGenericListener(BlockEntity.class, AMGenericEvents::attachCapabilityToBlockEntity);

        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(AMForgeEvents::tooltipEvent);
        }
    }

    private static void modEventsInitialize(IEventBus bus) {
        Constant.LOGGER.debug("Initializing mod events");
        CompactInitializer.init(bus);
        bus.addListener(AMManage::modCommon);
        bus.register(AMModEvents.class);
    }

    private static void modCommon(final FMLCommonSetupEvent e) {
        AMNetwork.init();
    }
}
