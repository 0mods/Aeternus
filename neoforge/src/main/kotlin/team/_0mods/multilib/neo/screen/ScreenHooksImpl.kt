package team._0mods.multilib.neo.screen

import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.screens.Screen
import team._0mods.multilib.client.hooks.ScreenHooks

internal class ScreenHooksImpl: ScreenHooks {
    override fun getNarratables(screen: Screen): List<NarratableEntry> = screen.narratables

    override fun getRenderables(screen: Screen): List<Renderable> = screen.renderables

    override fun <T> addWidget(
        screen: Screen,
        listener: T
    ): T where T : GuiEventListener, T : NarratableEntry = screen.addWidget(listener)

    override fun <T : Renderable> addRenderableOnly(
        screen: Screen,
        listener: T
    ): T = screen.addRenderableOnly(listener)

    override fun <T> addRenderableWidget(
        screen: Screen,
        widget: T
    ): T where T : AbstractWidget, T : Renderable, T : NarratableEntry = screen.addRenderableWidget(widget)
}