/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.impl.research.json

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.ResearchSettings
import team._0mods.aeternus.api.magic.research.book.ResearchAlignment
import team._0mods.aeternus.api.magic.research.book.ResearchBookMetadata
import team._0mods.aeternus.api.magic.research.book.ResearchShape
import team._0mods.aeternus.api.magic.research.of
import team._0mods.aeternus.api.util.mcTranslate
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.api.util.toRLList
import team._0mods.aeternus.common.init.AeternusCorePlugin

@Serializable
data class JSONResearch(
    val dependencies: List<String> = emptyList(),
    @SerialName("etherium_count") val etheriumCount: Double = 0.0,
    val triggers: List<String> = emptyList(),
    @Required
    @SerialName("book_meta")
    val metadata: JSONBookMetadata
): Research {
    override val etheriumNeedValue: Double
        get() = etheriumCount

    override val bookMetadata: ResearchBookMetadata
        get() = metadata

    override val settings: ResearchSettings
        get() = ResearchSettings.of(
            AeternusCorePlugin.triggerRegistry.getByIdList(triggers.toRLList()).toList(),
            AeternusCorePlugin.researchRegistry.getByIdList(dependencies.toRLList()).toList()
        )
}

@Serializable
data class JSONBookMetadata(
    @Required val name: String,
    @Required val description: String,
    val texture: String = "aeternus:textures/research/empty.png",
    @SerialName("book_pos") val inBookPosition: JSONInBookPosition = JSONInBookPosition(),
    @SerialName("align") val alignId: Int = 0,
    @SerialName("shape") val shapeId: Int = 0
): ResearchBookMetadata {
    override val title: Component
        get() = name.mcTranslate

    override val desc: Component
        get() = description.mcTranslate

    override val icon: ResourceLocation
        get() = texture.rl

    override val alignment: ResearchAlignment
        get() = ResearchAlignment.getById(alignId)

    override val shape: ResearchShape
        get() = ResearchShape.getById(shapeId)
}

@Serializable
data class JSONInBookPosition(val x: Int = 0, val y: Int = 0)

@Serializable
open class PolyResearchTrigger<T>(private val idPoly: String, @Polymorphic private val valuePoly: T?)

@Serializable
open class OnlyNamedResearchTrigger(val id: String): PolyResearchTrigger<Unit>(id, null)

@Serializable
class StringResearchTrigger(val id: String, val value: String): PolyResearchTrigger<String>(id, value)

@Serializable
class BooleanResearchTrigger(val id: String, val value: Boolean): PolyResearchTrigger<Boolean>(id, value)

@Serializable
open class NumeralResearchTrigger<T>(private val idN: String, @Polymorphic private val valueN: T): PolyResearchTrigger<T>(idN, valueN)

@Serializable
class IntResearchTrigger(val id: String, val value: Int): NumeralResearchTrigger<Int>(id, value)

class DoubleResearchTrigger(val id: String, val value: Double): NumeralResearchTrigger<Double>(id, value)
