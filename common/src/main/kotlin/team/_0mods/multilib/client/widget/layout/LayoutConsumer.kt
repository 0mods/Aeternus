package team._0mods.multilib.client.widget.layout

import net.minecraft.client.gui.components.AbstractWidget

interface LayoutConsumer {
    fun addLayoutWidget(widget: AbstractWidget)

    fun x(): Int
    fun y(): Int

    fun width(): Int
    fun height(): Int
}