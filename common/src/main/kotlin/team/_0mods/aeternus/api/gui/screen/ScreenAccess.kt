package team._0mods.aeternus.api.gui.screen

import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.screens.Screen

interface ScreenAccess {
    var screen: Screen

    val narratables: List<NarratableEntry>

    val renderedWidgets: List<Renderable>

    fun <T> addRenderableWidget(widget: T): T where T: AbstractWidget, T: Renderable, T: NarratableEntry

    fun <T: Renderable> addRenderableOnly(listener: T): T

    fun <T> addWidget(listener: T) where T: GuiEventListener, T: NarratableEntry
}