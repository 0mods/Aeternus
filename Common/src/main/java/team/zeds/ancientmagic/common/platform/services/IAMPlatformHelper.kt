package team.zeds.ancientmagic.common.platform.services

import net.minecraft.world.entity.player.Player
import team.zeds.ancientmagic.common.api.cap.PlayerMagic
import team.zeds.ancientmagic.common.api.helper.IHandleStack
import team.zeds.ancientmagic.common.api.helper.IStackHelper
import team.zeds.ancientmagic.common.api.network.IAMNetwork
import team.zeds.ancientmagic.common.api.network.IAMPacket
import team.zeds.ancientmagic.common.api.registry.IAMMultiblocks
import team.zeds.ancientmagic.common.api.registry.IAMRegistryEntry

interface IAMPlatformHelper {
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    fun getPlatformName(): String
    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    fun isModLoaded(modId: String): Boolean

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    fun isDeveloperment(): Boolean

    //ANCIENTMAGIC VALUES
    fun getIHandleStackForAltarBlockEntity(contentChange: Runnable): IHandleStack<*>
    fun getIHandleStackForAltarPedestalBlockEntity(contentChange: Runnable): IHandleStack<*>
    fun getIStackHelper(): IStackHelper
    fun getIHandleStackForAltarBlockEntityRecipeInventory(size: Int): IHandleStack<*>
    fun getPlayerMagic(player: Player): PlayerMagic<*>? // returns null if foreach value is null
    fun getIAMNetwork(): IAMNetwork
    fun getS2CPlayerPacket(): IAMPacket<*>?
    fun getC2SPlayerPacket(): IAMPacket<*>?
    fun getOldPlayerMagic(player: Player): PlayerMagic<*>?
    fun getNewPlayerMagic(player: Player): PlayerMagic<*>?
    fun getIAMRegistryEntry(): IAMRegistryEntry
    fun getIAMMultiblocks(): IAMMultiblocks
}