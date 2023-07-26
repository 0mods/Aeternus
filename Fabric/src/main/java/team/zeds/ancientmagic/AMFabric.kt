package team.zeds.ancientmagic

import net.fabricmc.api.ModInitializer

class AMFabric: ModInitializer {
    override fun onInitialize() {
        AMConstant.LOGGER.info("Hello Fabric world!")
        AMCommonClass.init()
    }
}