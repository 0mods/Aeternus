/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.impl.research.json

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.ExtraCodecs
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.*
import team._0mods.aeternus.api.magic.research.book.ResearchBookMetadata
import team._0mods.aeternus.common.init.AeternusCorePlugin
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.api.util.toRLList

@ApiStatus.Internal
@JvmRecord
data class ResearchType(
    val id: String,
    val dependList: List<String>,
    val etheriumCount: Double,
    val triggerList: List<String>,
    val metadata: BookMetadataType
): Research {
    companion object {
        val codec: Codec<ResearchType> = ExtraCodecs.catchDecoderException(
            RecordCodecBuilder.create {
                it.group(
                    Codec.STRING.fieldOf("id").forGetter(ResearchType::id),
                    Codec.list(Codec.STRING).fieldOf("required_researches").forGetter(ResearchType::dependList),
                    Codec.DOUBLE.fieldOf("etherium").orElse(0.0).forGetter(ResearchType::etheriumCount),
                    Codec.list(Codec.STRING).fieldOf("triggers").forGetter(ResearchType::triggerList),
                    BookMetadataType.codec.fieldOf("book_meta").forGetter(ResearchType::metadata)
                )
                .apply(it, ::ResearchType)
            }
        )
    }

    override val name: ResourceLocation
        get() = id.rl

    override val etheriumNeedValue: Double
        get() = etheriumCount
    
    override val bookMetadata: ResearchBookMetadata
        get() = metadata

    override val settings: ResearchSettings
        get() = ResearchSettings.of(
            AeternusCorePlugin.triggerRegistry.getResearchTriggerListByIdList(triggerList.toRLList()).toList(),
            AeternusCorePlugin.researchRegistry.getResearchListByIdList(dependList.toRLList()).toList()
        )
}
