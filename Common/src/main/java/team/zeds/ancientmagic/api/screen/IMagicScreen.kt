package team.zeds.ancientmagic.api.screen

interface IMagicScreen {
    fun getBookClassifier(): MagicBookClassifier

    fun `is`(classifier: MagicBookClassifier): Boolean {
        return this.getBookClassifier() == classifier
    }

    enum class MagicBookClassifier {
        MAIN_PAGE, SUBPAGE
    }
}