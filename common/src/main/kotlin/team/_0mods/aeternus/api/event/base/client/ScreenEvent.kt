package team._0mods.aeternus.api.event.base.client

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult
import team._0mods.aeternus.api.event.result.EventResultHolder
import team._0mods.aeternus.api.gui.screen.ScreenAccess

interface ScreenEvent {
    companion object {
        @JvmField /* no getter generate */ val RENDER_GUI_OVERLAY = EventFactory.createNoResult<PostRenderGuiOverlay>()

        @JvmField val DEBUG_LEFT = EventFactory.createNoResult<DebugText>()
        @JvmField val DEBUG_RIGHT = EventFactory.createNoResult<DebugText>()

        @JvmField val INIT_PRE = EventFactory.createEventResult<ScreenInitPre>()
        @JvmField val INIT_POST = EventFactory.createNoResult<ScreenInitPost>()

        @JvmField val RENDER_PRE = EventFactory.createEventResult<ScreenRenderPre>()
        @JvmField val RENDER_POST = EventFactory.createNoResult<ScreenRenderPost>()

        @JvmField val RENDER_CONTAINER_BACKGROUND = EventFactory.createNoResult<ContainerMenuRenderBackground>()
        @JvmField val RENDER_CONTAINER_FOREGROUND = EventFactory.createNoResult<ContainerMenuRenderForeground>()

        @JvmField val SET_SCREEN = EventFactory.createNoResult<SetScreen>()
    }

    fun interface PostRenderGuiOverlay {
        fun render(stack: PoseStack, partialTick: Float)
    }

    fun interface DebugText {
        fun render(texts: List<String>)
    }

    fun interface ScreenInitPre {
        fun init(screen: Screen, access: ScreenAccess): EventResult
    }

    fun interface ScreenInitPost {
        fun init(screen: Screen, access: ScreenAccess)
    }

    fun interface ScreenRenderPre {
        fun render(screen: Screen, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float): EventResult
    }

    fun interface ScreenRenderPost {
        fun render(screen: Screen, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    fun interface ContainerMenuRenderBackground {
        fun render(screen: AbstractContainerScreen<*>, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    fun interface ContainerMenuRenderForeground {
        fun render(screen: AbstractContainerScreen<*>, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    fun interface SetScreen {
        fun setScreen(screen: Screen): EventResultHolder<Screen>
    }
}
