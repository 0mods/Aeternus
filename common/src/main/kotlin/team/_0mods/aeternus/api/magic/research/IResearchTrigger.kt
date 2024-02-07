package team._0mods.aeternus.api.magic.research

interface IResearchTrigger {
    /**
     * Trigger Logic. This is where all the basic operation of calculating the event takes place. If the conditions inside it are met, it returns true
     *
     * Returns [Boolean]
     */
    fun onAction(): Boolean
}
