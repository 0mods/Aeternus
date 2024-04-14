package team._0mods.aeternus.common.impl.research

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.ResearchBookMetadata

internal class ResearchBookMetadataImpl(
    override val title: Component,
    override val desc: Component,
    override val icon: ResourceLocation,
    override val position: Pair<Int, Int>
) : ResearchBookMetadata
