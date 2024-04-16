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
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.book.ResearchAlignment
import team._0mods.aeternus.api.magic.research.book.ResearchBookMetadata
import team._0mods.aeternus.api.magic.research.book.ResearchShape
import team._0mods.multilib.util.rl

@JvmRecord
data class BookMetadataType(
    val name: Name,
    val descText: Description,
    val texture: String,
    val inBookPosition: Position,
    val alignId: Int,
    val shapeId: Int
): ResearchBookMetadata {
    companion object {
        val codec: Codec<BookMetadataType> = RecordCodecBuilder.create {
            it.group(
                Name.codec.fieldOf("name").forGetter(BookMetadataType::name),
                Description.codec.fieldOf("desc").forGetter(BookMetadataType::descText),
                Codec.STRING.fieldOf("texture").orElse("aeternus:textures/empty_icon").forGetter(BookMetadataType::texture),
                Position.codec.fieldOf("position").orElse(Position(0, 0)).forGetter(BookMetadataType::inBookPosition),
                Codec.INT.fieldOf("alignment").orElse(0).forGetter(BookMetadataType::alignId),
                Codec.INT.fieldOf("shape").orElse(0).forGetter(BookMetadataType::shapeId)
            ).apply(it, ::BookMetadataType)
        }
    }

    override val title: Component
        get() = this.name.asComponent

    override val icon: ResourceLocation
        get() = texture.rl

    override val offset: Pair<Int, Int>
        get() = inBookPosition.x to inBookPosition.y

    override val desc: Component
        get() = descText.asComponent

    override val alignment: ResearchAlignment
        get() = ResearchAlignment.getById(alignId)

    override val shape: ResearchShape
        get() = ResearchShape.getById(shapeId)

    @JvmRecord
    data class Position(val x: Int, val y: Int) {
        companion object {
            val codec: Codec<Position> = RecordCodecBuilder.create {
                it.group(
                    Codec.INT.fieldOf("x").forGetter(Position::x),
                    Codec.INT.fieldOf("y").forGetter(Position::y)
                ).apply(it, BookMetadataType::Position)
            }
        }
    }

    data class Name(val type: String, val value: String) {
        companion object {
            val codec: Codec<Name> = RecordCodecBuilder.create {
                it.group(
                    Codec.STRING.fieldOf("type").orElse("string").forGetter { name -> name.type },
                    Codec.STRING.fieldOf("value").forGetter { name -> name.value }
                ).apply(it, BookMetadataType::Name)
            }
        }

        val asComponent: Component
            get() {
                return if (this.type == "string") Component.literal(this.value)
                else Component.translatable(this.value)
            }
    }

    data class Description(val type: String, val value: String) {
        companion object {
            val codec: Codec<Description> = RecordCodecBuilder.create {
                it.group(
                    Codec.STRING.fieldOf("type").orElse("string").forGetter { name -> name.type },
                    Codec.STRING.fieldOf("value").forGetter { name -> name.value }
                ).apply(it, BookMetadataType::Description)
            }
        }

        val asComponent: Component
            get() {
                return if (this.type == "string") Component.literal(this.value)
                else Component.translatable(this.value)
            }
    }
}
