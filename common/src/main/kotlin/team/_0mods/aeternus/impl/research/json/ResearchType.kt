package team._0mods.aeternus.impl.research.json

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.ExtraCodecs
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.IResearch
import team._0mods.aeternus.api.magic.research.IResearchBookMetadata
import team._0mods.aeternus.api.magic.research.IResearchTrigger
import team._0mods.aeternus.init.AeternusCorePlugin
import team._0mods.aeternus.api.util.toRL
import team._0mods.aeternus.api.util.toRLList

@ApiStatus.Internal
@JvmRecord
data class ResearchType(
    val id: String,
    val dependList: List<String>,
    val etheriumCount: Double,
    val triggerList: List<String>,
    val metadata: BookMetadataType
): IResearch {
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
        get() = id.toRL()

    override val depends: List<IResearch>
        get() = AeternusCorePlugin.researchRegistry.getResearchListByIdList(dependList.toRLList()).toList()

    override val etheriumNeedValue: Double
        get() = etheriumCount

    override val triggers: List<IResearchTrigger>
        get() = AeternusCorePlugin.triggerRegistry.getResearchTriggerListByIdList(triggerList.toRLList()).toList()

    override val bookMetadata: IResearchBookMetadata
        get() = metadata
}
