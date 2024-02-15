package team._0mods.aeternus.common.impl.screen

import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.screens.Screen
import team._0mods.aeternus.api.gui.screen.ScreenAccess
import team._0mods.aeternus.service.ServiceProvider

class ScreenAccessImpl(override var screen: Screen): ScreenAccess {
    private val screenHooks = ServiceProvider.event.screenHooks // Simplification. I don't want to call the same class 300 times

    override val narratables: List<NarratableEntry>
        get() = screenHooks.getNarratables(this.screen)

    override val renderedWidgets: List<Renderable>
        get() = screenHooks.getRenderables(this.screen)

    override fun <T : AbstractWidget> addRenderableWidget(widget: T): T where T : Renderable, T : NarratableEntry =
            screenHooks.addRenderableWidget(this.screen, widget)

    override fun <T : Renderable> addRenderableOnly(listener: T): T = screenHooks.addRenderableOnly(screen, listener)

    override fun <T : GuiEventListener> addWidget(listener: T) where T : NarratableEntry {
        screenHooks.addWidget(this.screen, listener)
    }
}