package team.zeds.ancientmagic.api.screen

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team.zeds.ancientmagic.api.magic.MagicTypes
import team.zeds.ancientmagic.api.mod.Constant
import team.zeds.ancientmagic.client.packet.ClientPlayerMagicData

abstract class MagicBookScreen(component: Component): Screen(component), IMagicBookScreen {
    private val imgWidth: Int = 436
    private val imgHeight: Int = 232

    @Deprecated("Don't use MC method! Use from MagicBook.",
        ReplaceWith("render", "net.minecraft.client.gui.GuiGraphics",
        "kotlin.Int", "kotlin.Int", "kotlin.Float", "kotlin.Int", "kotlin.Int"),
        DeprecationLevel.HIDDEN
    )
    override fun render(guiGraphics: GuiGraphics, xMouse: Int, yMouse: Int, partialTick: Float) {
        super.render(guiGraphics, xMouse, yMouse, partialTick)
        val w = ((this.width / 2) - (imgWidth / 2))
        val h = ((this.height / 2) - (imgHeight / 2))

        val playerDisplayWidth = 64
        val playerDisplayHeight = 88
        val tagDisplayWidth = 92
        val tagDisplayHeight = 16

        if (this.getBookClassifier() == IMagicBookScreen.MagicBookClassifier.MAIN_PAGE) {
            guiGraphics.blit(textureGen("book_screen"), w, h, 0f, 0f, imgWidth, imgHeight, imgWidth, imgHeight)
            guiGraphics.blit(
                textureGen("player_frame"), w + 66, h + 29, 0f, 0f, playerDisplayWidth, playerDisplayHeight,
                playerDisplayWidth, playerDisplayHeight
            )
            guiGraphics.blit(
                textureGen("tag_frame"), w + 59, h + 119, 0f, 0f, tagDisplayWidth, tagDisplayHeight,
                tagDisplayWidth, tagDisplayHeight
            )
            val player = this.minecraft!!.player!!
            val xPos: Int = w + 97
            val yPos: Int = h + 111

            InventoryScreen.renderEntityInInventoryFollowsMouse(
                guiGraphics, xPos, yPos, 35, (xPos - xMouse).toFloat(),
                (yPos - yMouse).toFloat(), player
            )
            guiGraphics.drawString(font, Component.translatable(
                "magic.${Constant.KEY}.level", MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData()).getTranslation()
            ),
                w + 56, h + 122, ChatFormatting.WHITE.id)
            render(guiGraphics, xMouse, yMouse, partialTick, w, h)
        } else render(guiGraphics, xMouse, yMouse, partialTick, w, h)
        super.render(guiGraphics, 0, 0, partialTick)
    }

    fun render(guiGraphics: GuiGraphics, xMouse: Int, yMouse: Int, partialTick: Float, width: Int, height: Int) {}

    fun textureGen(name: String): ResourceLocation {
        return ResourceLocation(Constant.KEY, "textures/screen/magicbook/${name}.png")
    }

    override fun isPauseScreen(): Boolean = false
}
