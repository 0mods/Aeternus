package team.zeds.ancientmagic.compact;

import team.zeds.ancientmagic.api.mod.Constant;
import team.zeds.ancientmagic.compact.curios.AMCurio;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class CompactInitializer {
    public static void init(IEventBus bus) {
        if (CompactInitializer.getCuriosLoaded()) bus.addListener(AMCurio::createCurioSlots);
    }

    public static boolean getWaystonesLoaded() {
        return CompactInitializer.getModLoaded(Constant.WAYSTONES_KEY);
    }

    public static boolean getCuriosLoaded() {
        return CompactInitializer.getModLoaded(Constant.CURIO_KEY);
    }

    public static boolean getModLoaded(String modName) {
        return ModList.get().isLoaded(modName);
    }
}
