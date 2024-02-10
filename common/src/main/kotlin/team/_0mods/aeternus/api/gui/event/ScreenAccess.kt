package team._0mods.aeternus.api.gui.event

import jdk.internal.org.jline.reader.Widget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.screens.Screen

interface ScreenAccess {
    var screen: Screen

    val narratables: List<NarratableEntry>

    val renderedWidgets: List<Widget>

    fun <T: Widget> addRenderableOnly(listener: T): T

    fun <T> addWidget(listener: T) where T: GuiEventListener, T: NarratableEntry
}