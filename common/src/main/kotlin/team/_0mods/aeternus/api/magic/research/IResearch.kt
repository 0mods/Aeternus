package team._0mods.aeternus.api.magic.research

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.text.TranslationBuilder
import team._0mods.aeternus.rl

interface IResearch {
    /**
     * Research name.
     *
     * Returns [ResourceLocation]
     */
    val name: ResourceLocation

    /**
     * Translatable Research name.
     *
     * Returns [Component]
     */
    val translation: Component
        get() = TranslationBuilder.api("research.${name}")

    /**
     * Research texture on book.
     *
     * Returns [ResourceLocation]
     */
    val icon: ResourceLocation
        get() = "aeternus:textures/empty".rl

    /**
     * Array of requirement researches for this research.
     * It could be empty.
     * If previous researches is not opened, this research can't be opened
     *
     * Returns [Array] of [IResearch]es
     */
    val depends: List<IResearch>

    /**
     * Haven't a javadoc. Sorry! I'm Lazy
     */
    fun addRequirementResearch(vararg research: IResearch)

    /**
     * Count of consuming Etherium size.
     *
     * Returns count of needed etherium
     */
    val etheriumNeedValue: Double
        get() = 0.0

    /**
     * Triggers, after which it opens current research
     *
     * Returns [Array] of [IResearchTrigger]
     */
    val triggers: List<IResearchTrigger>

    fun addTriggers(vararg trigger: IResearchTrigger)
}
