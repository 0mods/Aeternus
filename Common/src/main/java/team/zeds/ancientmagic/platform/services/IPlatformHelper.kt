package team.zeds.ancientmagic.platform.services

import team.zeds.ancientmagic.api.helper.IHandleStack
import team.zeds.ancientmagic.api.helper.IStackHelper

interface IPlatformHelper {
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
    fun getIStackHandler(): IHandleStack
    fun getIStackHelper(): IStackHelper
}