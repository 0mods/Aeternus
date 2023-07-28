package team.zeds.ancientmagic.platform.services

import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.entity.BlockEntityType
import team.zeds.ancientmagic.api.cap.PlayerMagic
import team.zeds.ancientmagic.api.helper.IHandleStack
import team.zeds.ancientmagic.api.helper.IStackHelper
import team.zeds.ancientmagic.api.network.IAMNetwork
import team.zeds.ancientmagic.api.network.IAMPacket
import team.zeds.ancientmagic.api.recipe.AMRecipeSerializer
import team.zeds.ancientmagic.api.registry.IAMRegistryEntry
import team.zeds.ancientmagic.block.AltarPedestalBlock
import team.zeds.ancientmagic.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.recipes.AltarRecipe
import team.zeds.ancientmagic.recipes.ManaGenerationRecipe

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
    fun getIHandleStackForAltarBlockEntity(contentChange: Runnable): IHandleStack
    fun getIHandleStackForAltarPedestalBlockEntity(contentChange: Runnable): IHandleStack
    fun getIStackHelper(): IStackHelper
    fun getAltarBlockEntityType(): BlockEntityType<AltarBlockEntity>
    fun getAltarPedestalBlockEntityType(): BlockEntityType<AltarBlockEntity>
    fun getIHandleStackForAltarBlockEntityRecipeInventory(size: Int): IHandleStack
    fun getAltarPedestalBlock(): AltarPedestalBlock
    fun getAltarRecipeSerializer(): AMRecipeSerializer<AltarRecipe>
    fun getManaRecipeSerializer(): AMRecipeSerializer<ManaGenerationRecipe>
    fun getPlayerMagic(player: Player): PlayerMagic? // returns null if foreach value is null
    fun getIAMNetwork(): IAMNetwork
    fun getS2CPlayerPacket(): IAMPacket<*>
    fun getC2SPlayerPacket(): IAMPacket<*>
    fun getOldPlayerMagic(player: Player): PlayerMagic?
    fun getNewPlayerMagic(player: Player): PlayerMagic?
    fun getIAMRegistryEntry(): IAMRegistryEntry
}