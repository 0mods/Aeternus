package team.zeds.ancientmagic.fabric.platform

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.world.entity.player.Player
import team.zeds.ancientmagic.common.api.cap.PlayerMagic
import team.zeds.ancientmagic.common.api.helper.IHandleStack
import team.zeds.ancientmagic.common.api.helper.IStackHelper
import team.zeds.ancientmagic.common.api.network.IAMNetwork
import team.zeds.ancientmagic.common.api.network.IAMPacket
import team.zeds.ancientmagic.common.api.registry.IAMRegistryEntry
import team.zeds.ancientmagic.fabric.helper.HandleStack
import team.zeds.ancientmagic.fabric.helper.StackHelper
import team.zeds.ancientmagic.fabric.registries.AMRegistry
import team.zeds.ancientmagic.common.platform.services.IAMPlatformHelper
import team.zeds.ancientmagic.fabric.network.AMNetwork

class FabricPlatformHelper: IAMPlatformHelper {
    override fun getPlatformName(): String = "Fabric"

    override fun isModLoaded(modId: String): Boolean = FabricLoader.getInstance().isModLoaded(modId)

    override fun isDeveloperment(): Boolean = FabricLoader.getInstance().isDevelopmentEnvironment

    override fun getIHandleStackForAltarBlockEntity(contentChange: Runnable): IHandleStack =
        HandleStack.create(2, contentChange) { buildIn ->
            buildIn.setDefaultSlotLimit(1)
            buildIn.setCanExtract { buildIn.getItem(1).isEmpty }
            buildIn.setOutputSlots(1)
        }

    override fun getIHandleStackForAltarPedestalBlockEntity(contentChange: Runnable): IHandleStack =
        HandleStack.create(1, contentChange) {
            it.setDefaultSlotLimit(1)
        }

    override fun getIStackHelper(): IStackHelper = StackHelper

    override fun getIHandleStackForAltarBlockEntityRecipeInventory(size: Int): IHandleStack = HandleStack.create(9)

    override fun getPlayerMagic(player: Player): PlayerMagic? {
        TODO("Not yet implemented")
    }

    override fun getIAMNetwork(): IAMNetwork = AMNetwork()

    override fun getS2CPlayerPacket(): IAMPacket<*> {
        TODO("Not yet implemented")
    }

    override fun getC2SPlayerPacket(): IAMPacket<*> {
        TODO("Not yet implemented")
    }

    override fun getOldPlayerMagic(player: Player): PlayerMagic? {
        TODO("Not yet implemented")
    }

    override fun getNewPlayerMagic(player: Player): PlayerMagic? {
        TODO("Not yet implemented")
    }

    override fun getIAMRegistryEntry(): IAMRegistryEntry = AMRegistry
}