package team.zeds.ancientmagic.common.client.screen

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import team.zeds.ancientmagic.common.api.screen.AbstractMagicScreen
import team.zeds.ancientmagic.common.api.screen.IMagicScreen
import team.zeds.ancientmagic.common.api.screen.widget.ButtonBase

class MagicScreen: AbstractMagicScreen(Component.empty()) {
    override fun init() {
        this.addRenderableWidget(
            ButtonBase.builder(Component.empty()) {
                this.minecraft!!.player!!.displayClientMessage(
                    Component.literal(
                        "It's Work!"
                    ), false
                )
            }.size(16, 16).build(ItemStack(Blocks.AMETHYST_BLOCK))
        )
    }
    override fun getBookClassifier(): IMagicScreen.MagicBookClassifier = IMagicScreen.MagicBookClassifier.MAIN_PAGE
}
