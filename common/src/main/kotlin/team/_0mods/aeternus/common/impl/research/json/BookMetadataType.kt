package team._0mods.aeternus.common.impl.research.json

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.ResearchBookMetadata
import team._0mods.aeternus.api.util.rl

@JvmRecord
data class BookMetadataType(
        val name: Name,
        val texture: String,
        val inBookPosition: Position
): ResearchBookMetadata {
    companion object {
        val codec: Codec<BookMetadataType> = RecordCodecBuilder.create {
            it.group(
                Name.codec.fieldOf("name").forGetter(BookMetadataType::name),
                Codec.STRING.fieldOf("texture").orElse("aeternus:textures/empty_icon").forGetter(BookMetadataType::texture),
                Position.codec.fieldOf("position").orElse(Position(0, 0)).forGetter(BookMetadataType::inBookPosition)
            ).apply(it, ::BookMetadataType)
        }
    }

    override val translation: Component
        get() = this.name.asComponent

    override val icon: ResourceLocation
        get() = texture.rl

    override val position: Pair<Int, Int>
        get() = inBookPosition.x to inBookPosition.y

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
        // On java: getAsComponent()
        val asComponent: Component
            get() {
                return if (this.type == "string") Component.literal(this.value)
                else Component.translatable(this.value)
            }
    }
}