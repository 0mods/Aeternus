package team.zeds.ancientmagic.api.screen.widget

import com.mojang.blaze3d.platform.Lighting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class ButtonBase: Button {
    private val x: Int
    private val y: Int
    private val bbWidth: Int
    private val bbHeight: Int
    private val text: Component?
    private val reloc: ResourceLocation
    private val stack: ItemStack
    private val stackedTexture: Boolean

    constructor(builder: Builder, reloc: ResourceLocation): super(builder) {
        this.x = builder.x
        this.y = builder.y
        this.bbWidth = builder.width
        this.bbHeight = builder.height
        this.text = builder.message
        this.reloc = reloc
        this.stack = ItemStack.EMPTY
        this.stackedTexture = false
    }

    constructor(builder: Builder, stack: ItemStack): super(builder) {
        this.x = builder.x
        this.y = builder.y
        this.bbWidth = builder.width
        this.bbHeight = builder.height
        this.text = builder.message
        this.reloc = ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "")
        this.stack = stack
        this.stackedTexture = true
    }

    override fun renderWidget(graphics: GuiGraphics, xMouse: Int, yMouse: Int, partialTick: Float) {
        val minecraft = Minecraft.getInstance()
        val font = minecraft.font

        if (this.text != null || this.text != Component.empty())
            graphics.drawString(font, this.text!!, this.x, this.y + this.bbWidth / 4, 0xFFFFFFF)

        if (stackedTexture) {
            if (!this.stack.isEmpty) {
                Lighting.setupFor3DItems()

                graphics.renderItem(this.stack, this.x + 2, this.y + 2)
                graphics.renderItemDecorations(font, this.stack, this.x + 2, this.y + 2)
            } else graphics.blit(this.reloc, this.x, this.y, 0F,
                (if (isCursorAtButton(x, y)) height else 0).toFloat(), this.bbWidth, this.bbHeight, this.bbWidth, this.bbHeight * 2)
        }
    }

    fun isCursorAtButton(cursorX: Int, cursorY: Int) : Boolean =
        cursorX >= this.x && cursorY >= this.y && cursorX <= this.x + this.bbWidth && cursorY <= this.y + this.bbHeight
}