package team.zeds.ancientmagic.compact;

import team.zeds.ancientmagic.api.mod.AMConstant;
import team.zeds.ancientmagic.compact.curios.AMCurio;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class CompactInitializer {
    public static void init(IEventBus bus) {
        if (CompactInitializer.getCuriosLoaded()) bus.addListener(AMCurio::createCurioSlots);
    }

    public static boolean getWaystonesLoaded() {
        return CompactInitializer.getModLoaded(AMConstant.WAYSTONES_KEY);
    }

    public static boolean getCuriosLoaded() {
        return CompactInitializer.getModLoaded(AMConstant.CURIO_KEY);
    }

    public static boolean getModLoaded(String modName) {
        return ModList.get().isLoaded(modName);
    }
}
