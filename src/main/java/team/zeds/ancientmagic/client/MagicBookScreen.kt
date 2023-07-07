package team.zeds.ancientmagic.client

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team.zeds.ancientmagic.api.magic.MagicTypes
import team.zeds.ancientmagic.api.mod.Constant
import team.zeds.ancientmagic.client.packet.ClientPlayerMagicData
import team.zeds.ancientmagic.init.registries.AMCapability

class MagicBookScreen: Screen(Component.empty()) {
    private val imgWidth: Int = 512
    private val imgHeight: Int = 256

    var guiGraphics: GuiGraphics? = null

    override fun render(guiGraphics: GuiGraphics, xMouse: Int, yMouse: Int, partialTick: Float) {
        this.guiGraphics = guiGraphics
        val w = ((this.width / 2) - (imgWidth / 2))
        val h = ((this.height / 2) - (imgHeight / 2))

        val playerDisplayWidth = 64
        val playerDisplayHeight = 88
        val tagDisplayWidth = 72
        val tagDisplayHeight = 16

        guiGraphics.blit(textureGen("player_display"), w + 66, h + 29, playerDisplayWidth, playerDisplayHeight,
            playerDisplayWidth, playerDisplayHeight)
        guiGraphics.blit(textureGen("tag_display"), w + 97, h + 111, tagDisplayWidth, tagDisplayHeight,
            tagDisplayWidth, tagDisplayHeight)
        guiGraphics.blit(textureGen("main"), w, h, 0f, 0f, imgWidth, imgHeight, imgWidth, imgHeight)

        val player = this.minecraft!!.player
        val xPos: Int = w + 97
        val yPos: Int = h + 111

        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, xPos, yPos, 35, (xPos - xMouse).toFloat(),
            (yPos - yMouse).toFloat(), player!!)

        guiGraphics.drawString(font, Component.translatable(
            "magic.${Constant.KEY}.level", MagicTypes.getByNumeration(ClientPlayerMagicData.playerData).getTranslation()
        ),
            w + 56, h + 122, ChatFormatting.WHITE.id)

    }

    private fun textureGen(name: String): ResourceLocation {
        return ResourceLocation(Constant.KEY, "textures/screen/magicbook/${name}.png")
    }

    override fun isPauseScreen(): Boolean = false
}