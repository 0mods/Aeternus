package team.zeromods.ancientmagic.event.call;

import api.ancientmagic.group.AncientMagicTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.event.forge.ItemsToTabs;
import team.zeromods.ancientmagic.event.forge.MagicFunction;

public class EventRegister {
    public static void init() {
        IEventBus forge = MinecraftForge.EVENT_BUS;
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        forgeEventsInitialize(forge);
        modEventsInitialize(mod);
    }

    private static void forgeEventsInitialize(IEventBus bus) {
        bus.addListener(AncientMagicTabs::registerTabs);
        bus.addListener(MagicFunction::tooltipEvent);
        bus.addListener(ItemsToTabs::addItems);
    }
    private static void modEventsInitialize(IEventBus bus) {
        CompactInitializer.init(bus);
    }
}
