package team.zeds.ancientmagic.api.screen

interface IMagicBookScreen {
    fun getBookClassifier(): MagicBookClassifier

    enum class MagicBookClassifier {
        MAIN_PAGE, SUBPAGE
    }
}