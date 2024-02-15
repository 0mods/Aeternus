package team._0mods.aeternus.api.magic.research

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

interface ResearchBookMetadata {
    /**
     * Translatable Research name.
     *
     * Returns [Component]
     */
    val translation: Component

    /**
     * Research texture.
     *
     * Returns [ResourceLocation]
     */
    val icon: ResourceLocation

    /**
     * Research position on book
     *
     * Returns [Pair] of Integer and Integer, where first Integer - x pos, but second Integer - y pos.
     */
    val position: Pair<Int, Int>
}