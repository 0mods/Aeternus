package team.zeromods.ancientmagic.compact;

import api.ancientmagic.mod.Constant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.zeromods.ancientmagic.compact.curios.AMCurio;

public class CompactInitializer {
    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        if (CompactInitializer.getCuriosLoaded()) bus.addListener(AMCurio::createCurioSlots);
    }

    public static boolean getWaystonesLoaded() {
        return CompactInitializer.getModLoaded(Constant.WaystonesKey);
    }

    public static boolean getCuriosLoaded() {
        return CompactInitializer.getModLoaded(Constant.CurioKey);
    }

    public static boolean getModLoaded(String modName) {
        return ModList.get().isLoaded(modName);
    }
}
