package team.zeds.ancientmagic.client

import net.minecraft.network.chat.Component
import team.zeds.ancientmagic.api.screen.IMagicBookScreen.MagicBookClassifier
import team.zeds.ancientmagic.api.screen.MagicBookScreen

class MagicBookMainScreen: MagicBookScreen(Component.empty()) {
    override fun getBookClassifier(): MagicBookClassifier = MagicBookClassifier.MAIN_PAGE

}