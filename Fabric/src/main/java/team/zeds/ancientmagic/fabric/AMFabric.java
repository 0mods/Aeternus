package team.zeds.ancientmagic.fabric;

import net.fabricmc.api.ModInitializer;
import team.zeds.ancientmagic.AMConstant;
import team.zeds.ancientmagic.fabric.registries.AMRegistry;

public class AMFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AMConstant.LOGGER.info("Hello Fabric world!");
        AMRegistry.initialize();
    }
}
