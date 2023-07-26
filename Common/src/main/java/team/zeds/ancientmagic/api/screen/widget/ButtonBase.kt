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

    constructor(x: Int, y: Int, width: Int, height: Int, message: Component, onPress: OnPress,
                createNarration: CreateNarration, reloc: ResourceLocation): super(
            x,
            y,
            width,
            height,
            message,
            onPress,
            createNarration
    ) {
        this.x = x
        this.y = y
        this.bbWidth = width
        this.bbHeight = height
        this.text = message
        this.reloc = reloc
        this.stack = ItemStack.EMPTY
        this.stackedTexture = false
    }

    constructor(x: Int, y: Int, width: Int, height: Int, message: Component, onPress: OnPress,
                createNarration: CreateNarration, stack: ItemStack):
            super(x, y, width, height, message, onPress, createNarration) {
        this.x = x
        this.y = y
        this.bbWidth = width
        this.bbHeight = height
        this.text = message
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
            } else graphics.blitNineSliced(
                WIDGETS_LOCATION,
                getX(), getY(), getWidth(), getHeight(), 20, 4, 200, 20, 0, getTextureY()
            )
        } else {
            val emptyLocation = ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "")
            if (this.reloc != emptyLocation) graphics.blit(this.reloc, this.x, this.y, 0F,
                (if (isCursorAtButton(x, y)) height else 0).toFloat(), this.bbWidth, this.bbHeight, this.bbWidth, this.bbHeight * 2)
            else graphics.blitNineSliced(
                WIDGETS_LOCATION,
                getX(), getY(), getWidth(), getHeight(), 20, 4, 200, 20, 0, getTextureY()
            )
        }
    }

    fun isCursorAtButton(cursorX: Int, cursorY: Int) : Boolean =
        cursorX >= this.x && cursorY >= this.y && cursorX <= this.x + this.bbWidth && cursorY <= this.y + this.bbHeight

    private fun getTextureY(): Int {
        var i = 1
        if (!active) {
            i = 0
        } else if (this.isHoveredOrFocused) {
            i = 2
        }
        return 46 + i * 20
    }

    companion object {
        @JvmStatic
        fun builder(component: Component, onPress: OnPress): Builder = Builder(component, onPress)
    }

    class Builder(component: Component, onPress: OnPress): Button.Builder(component, onPress) {
        @Deprecated("Don't use this method, it are invalid!",
            ReplaceWith("build(net.minecraft.resources.ResourceLocation) or build(net.minec)",
            "team.zeds.ancientmagic.api.gui.widget.ButtonBase.Builder"))
        override fun build(): Button {
            return super.build()
        }

        fun build(resourceLocation: ResourceLocation): ButtonBase {
            return ButtonBase(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration, resourceLocation)
        }

        fun build(stack: ItemStack): ButtonBase {
            return ButtonBase(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration, stack)
        }
    }
}