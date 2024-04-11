package team._0mods.multilib.ui

import net.minecraft.client.gui.GuiGraphics
import team._0mods.multilib.ui.animations.UIAnimation

interface IWidget {
    fun render(
        stack: GuiGraphics,
        x: Int, y: Int,
        screenWidth: Int, screenHeight: Int,
        widgetWidth: Int, widgetHeight: Int,
        mouseX: Int, mouseY: Int,
        partialTick: Float
    )

    fun renderWidget(
        stack: GuiGraphics,
        x: Int, y: Int,
        screenWidth: Int, screenHeight: Int,
        widgetWidth: Int, widgetHeight: Int,
        mouseX: Int, mouseY: Int,
        partialTick: Float
    )

    fun buttonPressed(
        x: Int, y: Int,
        screenWidth: Int, screenHeight: Int,
        widgetWidth: Int, widgetHeight: Int,
        mouseX: Int, mouseY: Int
    ): Boolean = false

    fun widgetButtonPressed(
        x: Int, y: Int,
        screenWidth: Int, screenHeight: Int,
        widgetWidth: Int, widgetHeight: Int,
        mouseX: Int, mouseY: Int
    ): Boolean = false
}

//@Serializable
//@Polymorphic(IWidget::class)
open class Widget: IWidget {
    internal val widgets = arrayListOf<IWidget>()
    internal val animations = arrayListOf<UIAnimation>()

    override fun render(
        stack: GuiGraphics,
        x: Int,
        y: Int,
        screenWidth: Int,
        screenHeight: Int,
        widgetWidth: Int,
        widgetHeight: Int,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        TODO("Not yet implemented")
    }

    override fun renderWidget(
        stack: GuiGraphics,
        x: Int,
        y: Int,
        screenWidth: Int,
        screenHeight: Int,
        widgetWidth: Int,
        widgetHeight: Int,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        TODO("Not yet implemented")
    }

}