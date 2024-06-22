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

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.ResearchSettings
import team._0mods.aeternus.api.magic.research.book.ResearchAlignment
import team._0mods.aeternus.api.magic.research.book.ResearchBookMetadata
import team._0mods.aeternus.api.magic.research.book.ResearchShape
import team._0mods.aeternus.api.magic.research.create
import team._0mods.aeternus.api.magic.research.of
import team._0mods.aeternus.api.util.toAPIRLList
import team._0mods.aeternus.platformredirect.common.init.AeternusCorePlugin
import team._0mods.aeternus.platformredirect.api.util.textTranslate

@Serializable
data class JSONResearch(
    val dependencies: List<String> = emptyList(),
    @SerialName("etherium_count") val etheriumCount: Double = 0.0,
    val triggers: List<String> = emptyList(),
    @Required
    @SerialName("book_meta")
    val metadata: JSONBookMetadata
) {
    val asResearch: Research = Research.create(
        ResearchSettings.of(
            AeternusCorePlugin.triggerRegistry.getByIdList(triggers.toAPIRLList),
            AeternusCorePlugin.researchRegistry.getByIdList(dependencies.toAPIRLList).toList()
        ),
        metadata.asBookMetadata,
        etheriumCount
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
) {
    val asBookMetadata: ResearchBookMetadata = ResearchBookMetadata.of(
        name.textTranslate,
        description.textTranslate,
        texture,
        inBookPosition.x to inBookPosition.y,
        ResearchAlignment.getById(alignId),
        ResearchShape.getById(shapeId)
    )
}

@Serializable
data class JSONInBookPosition(val x: Int = 0, val y: Int = 0)
