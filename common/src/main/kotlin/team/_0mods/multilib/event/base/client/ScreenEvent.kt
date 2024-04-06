/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package team._0mods.multilib.event.base.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import team._0mods.multilib.client.hooks.ScreenAccess
import team._0mods.multilib.event.core.EventFactory
import team._0mods.multilib.event.result.EventResult
import team._0mods.multilib.event.result.EventResultHolder

interface ScreenEvent {
    companion object {
        @JvmField val RENDER_HUD = EventFactory.createNoResult<RenderHud>()

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

    fun interface RenderHud {
        fun render(stack: GuiGraphics, partialTick: Float)
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
