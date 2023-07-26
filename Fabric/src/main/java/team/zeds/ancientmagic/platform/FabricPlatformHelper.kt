package team.zeds.ancientmagic.platform

import net.fabricmc.loader.api.FabricLoader
import team.zeds.ancientmagic.api.helper.IHandleStack
import team.zeds.ancientmagic.api.helper.IStackHelper
import team.zeds.ancientmagic.platform.services.IPlatformHelper

class FabricPlatformHelper: IPlatformHelper {
    override fun getPlatformName(): String = "Fabric"

    override fun isModLoaded(modId: String): Boolean = FabricLoader.getInstance().isModLoaded(modId)

    override fun isDeveloperment(): Boolean = FabricLoader.getInstance().isDevelopmentEnvironment

    override fun getIStackHandler(): IHandleStack {
        TODO("Not yet implemented")
    }

    override fun getIStackHelper(): IStackHelper {
        TODO("Not yet implemented")
    }
}