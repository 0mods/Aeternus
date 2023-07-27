package team.zeds.ancientmagic.api.screen.widget

import com.mojang.blaze3d.platform.Lighting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Button.CreateNarration
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import java.util.function.Supplier

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

    class Builder(component: Component, val onPress: OnPress): Button.Builder(component, onPress) {
        var createNarration: CreateNarration
        var tooltip: Tooltip? = null

        init {
            createNarration = CreateNarration { narr ->
                narr.get()
            }
        }

        @Deprecated("Don't use this method, it are invalid!",
            ReplaceWith("build(net.minecraft.resources.ResourceLocation) or build(net.minecraft.world.item.ItemStack)",
            "team.zeds.ancientmagic.api.gui.widget.ButtonBase.Builder"), level = DeprecationLevel.HIDDEN)
        override fun build(): Button {
            return super.build()
        }

        override fun pos(x: Int, y: Int): Builder {
            this.x = x
            this.y = y
            return this
        }

        override fun width(width: Int): Builder {
            this.width = width
            return this
        }

        override fun size(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            return this
        }

        override fun bounds(x: Int, y: Int, width: Int, height: Int): Builder {
            return this.pos(x, y).size(width, height)
        }

        override fun tooltip(tooltip: Tooltip?): Builder {
            this.tooltip = tooltip
            return this
        }

        override fun createNarration(createNarration: CreateNarration): Builder {
            this.createNarration = createNarration
            return this
        }

        fun build(resourceLocation: ResourceLocation): ButtonBase {
            val builder = ButtonBase(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration, resourceLocation)
            builder.tooltip = this.tooltip
            return builder
        }

        fun build(stack: ItemStack): ButtonBase {
            val builder = ButtonBase(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration, stack)
            builder.tooltip = this.tooltip
            return builder
        }
    }
}