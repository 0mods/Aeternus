package team._0mods.aeternus.api.magic.research

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.text.TranslationBuilder

interface IResearch {
    companion object {
        val registriedResearch: MutableList<IResearch> = mutableListOf()
    }

    /**
     * Research name.
     *
     * Returns [ResourceLocation]
     */
    fun getName(): ResourceLocation

    /**
     * Translatable Research name.
     *
     * Returns [Component]
     */
    fun getTranslation(): Component = TranslationBuilder.api("research.${getName()}")

    /**
     * Array of requirement researches for this research.
     * It could be empty.
     * If previous researches is not opened, this research can't be opened
     *
     * Returns [Array] of [IResearch]es
     */
    fun getRequirementResearches(): MutableMap<ResourceLocation, IResearch>

    /**
     * Haven't a javadoc. Sorry! I'm Lazy
     */
    fun addRequirementResearch(vararg research: IResearch)

    /**
     * Count of consuming Etherium size.
     *
     * Returns count of needed etherium
     */
    fun getEtheriumCountForOpen(): Double = 0.0

    /**
     * Triggers, after which it opens current research
     *
     * Returns [Array] of [IResearchTrigger]
     */
    fun getTriggers(): MutableMap<ResourceLocation, IResearchTrigger>

    fun addTriggers(vararg trigger: IResearchTrigger)

    fun serializeNBT(): CompoundTag

    fun deserializeNBT(tag: CompoundTag)

    /**
     * Don't implement this method! It is automatic registry
     */
    fun register() { registriedResearch.add(this) }
}
