package team._0mods.aeternus.api.magic.research

interface ResearchSettings {
    companion object

    /**
     * List of requirement researches for current research.
     * It could be empty.
     * If previous researches are not opened, this research can't be opened
     *
     * Returns [List] of [Research]es
     */
    val depends: List<Research>

    /**
     * Triggers, after which it opens current research
     *
     * Returns [List] of [ResearchTrigger]
     */
    val triggers: List<ResearchTrigger>

    /**
     * Adds to [depends] researches without which research cannot be opened
     */
    fun addRequirementResearch(vararg research: Research)

    /**
     * Adds triggers to [triggers] after which the research will open
     */
    fun addTriggers(vararg trigger: ResearchTrigger)
}
