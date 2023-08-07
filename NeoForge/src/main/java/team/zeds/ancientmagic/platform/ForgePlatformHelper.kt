package team.zeds.ancientmagic.platform

import net.minecraft.world.entity.player.Player
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLLoader
import team.zeds.ancientmagic.common.api.cap.PlayerMagic
import team.zeds.ancientmagic.common.api.helper.IHandleStack
import team.zeds.ancientmagic.common.api.helper.IStackHelper
import team.zeds.ancientmagic.common.api.network.IAMNetwork
import team.zeds.ancientmagic.common.api.network.IAMPacket
import team.zeds.ancientmagic.common.api.registry.IAMMultiblocks
import team.zeds.ancientmagic.common.api.registry.IAMRegistryEntry
import team.zeds.ancientmagic.common.platform.services.IAMPlatformHelper

class ForgePlatformHelper: IAMPlatformHelper {
    override fun getPlatformName(): String = "Forge"

    override fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId)

    override fun isDeveloperment(): Boolean = !FMLLoader.isProduction()

    override fun getIHandleStackForAltarBlockEntity(contentChange: Runnable): IHandleStack {
        TODO("Not yet implemented")
    }

    override fun getIHandleStackForAltarPedestalBlockEntity(contentChange: Runnable): IHandleStack {
        TODO("Not yet implemented")
    }

    override fun getIStackHelper(): IStackHelper {
        TODO("Not yet implemented")
    }

    override fun getIHandleStackForAltarBlockEntityRecipeInventory(size: Int): IHandleStack {
        TODO("Not yet implemented")
    }

    override fun getPlayerMagic(player: Player): PlayerMagic? {
        TODO("Not yet implemented")
    }

    override fun getIAMNetwork(): IAMNetwork {
        TODO("Not yet implemented")
    }

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

    override fun getIAMRegistryEntry(): IAMRegistryEntry {
        TODO("Not yet implemented")
    }

    override fun getIAMMultiblocks(): IAMMultiblocks {
        TODO("Not yet implemented")
    }
}