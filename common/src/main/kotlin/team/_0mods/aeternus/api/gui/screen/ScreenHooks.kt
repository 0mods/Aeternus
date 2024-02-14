package team._0mods.aeternus.api.gui.screen

import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.screens.Screen

interface ScreenHooks {
    fun getNarratables(screen: Screen): List<NarratableEntry>

    fun getRenderables(screen: Screen): List<Renderable>

    fun <T> addRenderableWidget(screen: Screen, widget: T): T where T: AbstractWidget, T: Renderable, T: NarratableEntry

    fun <T: Renderable> addRenderableOnly(screen: Screen, listener: T): T

    fun <T> addWidget(screen: Screen, listener: T): T where T: GuiEventListener, T: NarratableEntry
}