package team._0mods.aeternus.common.impl.research

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.book.ResearchAlignment
import team._0mods.aeternus.api.magic.research.book.ResearchBookMetadata
import team._0mods.aeternus.api.magic.research.book.ResearchShape

internal class ResearchBookMetadataImpl(
    override val title: Component,
    override val desc: Component,
    override val icon: ResourceLocation,
    override val offset: Pair<Int, Int>,
    override val alignment: ResearchAlignment,
    override val shape: ResearchShape
) : ResearchBookMetadata
