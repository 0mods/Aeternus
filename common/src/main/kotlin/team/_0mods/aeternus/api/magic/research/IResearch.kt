package team._0mods.aeternus.api.magic.research

import net.minecraft.resources.ResourceLocation

interface IResearch {
    /**
     * Research name.
     *
     * Returns [ResourceLocation]
     */
    val name: ResourceLocation

    /**
     * List of requirement researches for current research.
     * It could be empty.
     * If previous researches is not opened, this research can't be opened
     *
     * Returns [List] of [IResearch]es
     */
    val depends: List<IResearch>

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
     * Returns [List] of [IResearchTrigger]
     */
    val triggers: List<IResearchTrigger>

    val bookMetadata: IResearchBookMetadata

    /**
     * Haven't a javadoc. Sorry! I'm Lazy
     */
    fun addRequirementResearch(vararg research: IResearch) {}

    fun addTriggers(vararg trigger: IResearchTrigger) {}
}
