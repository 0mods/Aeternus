package team.zeromods.ancientmagic.compact;

import team.zeromods.ancientmagic.api.mod.Constant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import team.zeromods.ancientmagic.compact.curios.AMCurio;

public class CompactInitializer {
    public static void init(IEventBus bus) {
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
