/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api

import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.registry.MaterialRegistry
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.api.registry.ResearchTriggerRegistry

/**
 * Starts registration of the current plugin.
 * Works only on (Neo)Forge Mod Loader and only with [AeternusPlugin]
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class AeternusPluginInit

/**
 * Plugin base interface. For registry your plugin:
 *
 * On Fabric use entrypoint with "aeternus_plugin". [About entrypoints read there](https://fabricmc.net/wiki/documentation:entrypoint)
 *
 * On (Neo)Forge use [AeternusPluginInit] with plugin class
 *
 * For a more stable plugin, I suggest using Kotlin, not Java or any other JVM-like language
 */
interface AeternusPlugin {
    /**
     * Plugin's mod id for registry initialization
     *
     * @return [String] of mod id
     */
    val modId: String

    /**
     * This is a research registration.
     *
     * @param [reg] returns [ResearchRegistry] in order to register research
     */
    fun registerResearch(reg: ResearchRegistry) {}

    /**
     * This is a research trigger registration.
     *
     * @param [reg] returns [ResearchTriggerRegistry] in order to register research trigger
     */
    fun registerResearchTriggers(reg: ResearchTriggerRegistry) {}

    /**
     * This is a material registration
     *
     * @param [reg] returns [MaterialRegistry] in order to register material
     */
    @ApiStatus.NonExtendable
    @ApiStatus.Internal
    @ApiStatus.AvailableSince("1.0")
    fun registerMaterial(reg: MaterialRegistry) {
        throw IllegalAccessException("This function is not available in current version. Please, wait realisation!")
    }
}
