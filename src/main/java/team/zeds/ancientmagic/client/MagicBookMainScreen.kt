package team.zeds.ancientmagic.client

import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Blocks
import team.zeds.ancientmagic.api.screen.IMagicBookScreen.MagicBookClassifier
import team.zeds.ancientmagic.api.screen.MagicBookScreen
import team.zeds.ancientmagic.api.screen.widget.ButtonBase

class MagicBookMainScreen: MagicBookScreen(Component.empty()) {
    override fun init() {
        this.addRenderableWidget(
            ButtonBase(Button.builder(Component.empty()) {
                this.minecraft!!.player!!.displayClientMessage(
                    Component.literal(
                        "It's work!"
                    ), false
                )
            }.size(16, 16).pos(40, 40), Blocks.AMETHYST_BLOCK)
        )
    }

    override fun getBookClassifier(): MagicBookClassifier = MagicBookClassifier.MAIN_PAGE

}