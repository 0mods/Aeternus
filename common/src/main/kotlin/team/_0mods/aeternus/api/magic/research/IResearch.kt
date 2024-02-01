package team._0mods.aeternus.api.magic.research

import net.minecraft.network.chat.Component
import team._0mods.aeternus.api.text.TranslationBuilder

interface IResearch {
    /**
     * Research name. Returns [String]
     */
    fun getName(): String

    /**
     * Translatable Research name.
     *
     * Returns [Component]
     */
    fun getTranslation(): Component = TranslationBuilder.api("research.${getName()}")

    /**
     * Array of requirement researches for this research. It could be empty. If previous researches is not opened, this research can't be opened
     *
     * Returns [Array] of [IResearch]es
     */
    fun getRequirementResearches(): Array<out IResearch>

    fun getEtheriumCountForOpen(): Int = 0

    fun getTriggers(): Array<out IResearchTrigger>
}