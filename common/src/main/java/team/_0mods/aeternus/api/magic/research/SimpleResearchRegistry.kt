/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic.research

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.common.init.AeternusCorePlugin
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegated variant of [ResearchRegistry]
 *
 * Example:
 *
 * ```kt
 * val myResearch by SimpleResearchRegistry.register("modid:my_research") {
 *    Research.create(ResearchSettings.of() /* empty depends and triggers */, ResearchBookMetadata.of("Name!".mcText, "Description!".mcText, "mymodid:textures/my_research.png", ResearchAlignment.CENTER, ResearchShape.SQUARE))
 * }
 * ```
 */
object SimpleResearchRegistry {
    fun <T: Research> register(id: String, research: () -> T): ResearchDelegate<T> {
        var cId = id
        if (!cId.contains(':')) {
            LOGGER.warn("Failed to get modid for research with id '$id'! Using default modid '$ModId'")
            cId = "$ModId:$cId"
        }

        return this.register(cId, research)
    }

    fun <T: Research> register(id: ResourceLocation, research: () -> T): ResearchDelegate<T> {
        val lazyfied by lazy {
            AeternusCorePlugin.researchRegistry.register(id, research())
        }

        return ResearchDelegate { lazyfied }
    }

    class ResearchDelegate<T: Research>(private val research: () -> T): ReadOnlyProperty<Any?, T>, () -> T {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T = this()

        override fun invoke(): T = research()
    }
}
