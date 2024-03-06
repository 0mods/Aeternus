/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.multilib.forge.event

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraftforge.client.event.ClientChatEvent as ForgeChat
import net.minecraftforge.client.event.*
import net.minecraftforge.client.event.InputEvent as ForgeInput
import net.minecraftforge.event.TickEvent as Formultilibick
import net.minecraftforge.client.event.ScreenEvent as ForgeScreen
import net.minecraftforge.event.entity.player.*
import net.minecraftforge.event.level.*
import net.minecraftforge.eventbus.api.*
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import team._0mods.multilib.client.screen.ScreenAccessImpl
import team._0mods.multilib.event.base.client.*
import team._0mods.multilib.event.base.common.InteractionEvent
import team._0mods.multilib.impl.*

class ClientEventsHandler {
    private val positionCtx = ThreadLocal.withInitial(::TooltipPositionContext)

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun tooltip(e: ItemTooltipEvent) {
        TooltipEvent.ITEM.event.append(e.itemStack, e.toolTip, e.flags)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun tick(event: Formultilibick.ClientTickEvent) {
        if (event.phase == Formultilibick.Phase.START)
            ClientTickEvent.CLIENT_PRE.event.tick(Minecraft.getInstance())
        else if (event.phase == Formultilibick.Phase.END) ClientTickEvent.CLIENT_POST.event.tick(Minecraft.getInstance())
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun postRender(e: RenderGuiEvent.Post) {
        ScreenEvent.RENDER_GUI_OVERLAY.event.render(e.guiGraphics, e.partialTick)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun login(e: ClientPlayerNetworkEvent.LoggingIn) {
        ClientPlayerEvent.PLAYER_JOIN.event.onJoin(e.player)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun logout(e: ClientPlayerNetworkEvent.LoggingOut) {
        ClientPlayerEvent.PLAYER_LEAVE.event.onLeave(e.player)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun clone(e: ClientPlayerNetworkEvent.Clone) {
        ClientPlayerEvent.PLAYER_CLONE.event.onClone(e.oldPlayer, e.newPlayer)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun screenPre(e: ForgeScreen.Init.Pre) {
        if (ScreenEvent.INIT_PRE.event.init(e.screen, ScreenAccessImpl(e.screen)).isFalse) e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun screenPost(e: ForgeScreen.Init.Post) {
        ScreenEvent.INIT_POST.event.init(e.screen, ScreenAccessImpl(e.screen))
    }
    @SubscribeEvent(priority = EventPriority.HIGH)

    fun debugTest(e: CustomizeGuiOverlayEvent.DebugText) {
        if (Minecraft.getInstance().gui.debugOverlay.showDebugScreen()) {
            ScreenEvent.DEBUG_LEFT.event.render(e.left)
            ScreenEvent.DEBUG_LEFT.event.render(e.right)
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun chat(e: ForgeChat) {
        val process = ClientChatEvent.SEND.event.send(e.message, null)
        if (process.isFalse) e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun chatReceived(e: ClientChatReceivedEvent) {
        val process = ClientChatEvent.RECEIVED.event.process(e.boundChatType, e.message)

        if (process.isPresent) {
            if (process.isFalse) e.isCanceled = true
            else if (process.obj != null) e.message = process.obj!!
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun world(e: LevelEvent.Load) {
        if (e.level.isClientSide) {
            val world = e.level as ClientLevel
            ClientLifecycleEvent.CLIENT_LEVEL_LOAD.event.onChanged(world)
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun open(e: ForgeScreen.Opening) {
        val result = ScreenEvent.SET_SCREEN.event.setScreen(e.screen)
        if (result.isPresent) {
            if (result.isFalse) e.isCanceled = true
            else if (result.obj != null) e.newScreen = result.obj!!
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun renderPre(e: ForgeScreen.Render.Post) {
        ScreenEvent.RENDER_POST.event.render(e.screen, e.guiGraphics, e.mouseX, e.mouseY, e.partialTick)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun background(e: ContainerScreenEvent.Render.Background) {
        ScreenEvent.RENDER_CONTAINER_BACKGROUND.event.render(e.containerScreen, e.guiGraphics, e.mouseX, e.mouseY, Minecraft.getInstance().deltaFrameTime)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun foreground(e: ContainerScreenEvent.Render.Foreground) {
        ScreenEvent.RENDER_CONTAINER_FOREGROUND.event.render(e.containerScreen, e.guiGraphics, e.mouseX, e.mouseY, Minecraft.getInstance().deltaFrameTime)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun playerRMC(e: PlayerInteractEvent.RightClickEmpty) {
        InteractionEvent.CLIENT_RIGHT_CLICK_AIR.event.click(e.entity, e.hand)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun playerLMC(e: PlayerInteractEvent.LeftClickEmpty) {
        InteractionEvent.CLIENT_LEFT_CLICK_AIR.event.click(e.entity, e.hand)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun recipeUpdate(e: RecipesUpdatedEvent) {
        ClientRecipeUpdateEvent.EVENT.event.update(e.recipeManager)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun renderTooltip(e: RenderTooltipEvent.Pre) {
        val graphics = e.graphics
        TooltipEvent.additionalContext().item = e.itemStack

        try {
            if (TooltipEvent.RENDER_PRE.event.renderTooltip(graphics, e.components, e.x, e.y).isFalse) {
                e.isCanceled = true
                return
            }

            val pos = this.positionCtx.get()
            pos.reset(e.x, e.y)
            TooltipEvent.RENDER_MODIFY_POS.event.renderTooltip(graphics, pos)
            e.x = pos.tooltipX
            e.y = pos.tooltipY
        } finally {
            TooltipEvent.additionalContext().item = null
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun renderColorTooltip(e: RenderTooltipEvent.Color) {
        val graphics = e.graphics
        TooltipEvent.additionalContext().item = e.itemStack

        try {
            val color = TooltipColorContext.CONTEXT.get()
            color.reset()
            color.backgroundColor = e.backgroundStart
            color.outlineGradientTopColor = e.borderStart
            color.outlineGradientBottomColor = e.borderEnd
            TooltipEvent.RENDER_MODIFY_COLOR.event.renderTooltip(graphics, e.x,  e.y, color)
        } finally {
            TooltipEvent.additionalContext().item = null
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mousePreScroll(e: ForgeScreen.MouseScrolled.Pre) {
        if (ScreenInputEvent.MOUSE_SCROLLED_PRE.event.onScroll(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.deltaX, e.deltaY).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mousePostScroll(e: ForgeScreen.MouseScrolled.Post) {
        ScreenInputEvent.MOUSE_SCROLLED_POST.event.onScroll(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.deltaX, e.deltaY)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mouseClick(e: ForgeScreen.MouseButtonPressed.Pre) {
        if (ScreenInputEvent.MOUSE_CLICKED_PRE.event.onClicked(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.button).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mouseClick(e: ForgeScreen.MouseButtonPressed.Post) {
        ScreenInputEvent.MOUSE_CLICKED_POST.event.onClicked(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.button)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mouseDrag(e: ForgeScreen.MouseDragged.Pre) {
        if (ScreenInputEvent.MOUSE_DRAGGED_PRE.event.onDrag(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.mouseButton, e.dragX, e.dragY).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mouseDrag(e: ForgeScreen.MouseDragged.Post) {
        ScreenInputEvent.MOUSE_DRAGGED_POST.event.onDrag(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.mouseButton, e.dragX, e.dragY)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mouseReleased(e: ForgeScreen.MouseButtonReleased.Pre) {
        if (ScreenInputEvent.MOUSE_RELEASED_PRE.event.onRelease(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.button).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun mouseReleased(e: ForgeScreen.MouseButtonReleased.Post) {
        ScreenInputEvent.MOUSE_RELEASED_POST.event.onRelease(Minecraft.getInstance(), e.screen, e.mouseX, e.mouseY, e.button)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun charType(e: ForgeScreen.CharacterTyped.Pre) {
        if (ScreenInputEvent.CHAR_TYPED_PRE.event.onPress(Minecraft.getInstance(), e.screen, e.codePoint, e.modifiers).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun charType(e: ForgeScreen.CharacterTyped.Post) {
        ScreenInputEvent.CHAR_TYPED_POST.event.onPress(Minecraft.getInstance(), e.screen, e.codePoint, e.modifiers)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun charType(e: ForgeScreen.KeyPressed.Pre) {
        if (ScreenInputEvent.KEY_PRESSED_PRE.event.onPress(Minecraft.getInstance(), e.screen, e.keyCode, e.scanCode, e.modifiers).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun charType(e: ForgeScreen.KeyPressed.Post) {
        ScreenInputEvent.KEY_PRESSED_POST.event.onPress(Minecraft.getInstance(), e.screen, e.keyCode, e.scanCode, e.modifiers)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun charType(e: ForgeScreen.KeyReleased.Pre) {
        if (ScreenInputEvent.KEY_RELEASED_PRE.event.onRelease(Minecraft.getInstance(), e.screen, e.keyCode, e.scanCode, e.modifiers).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun charType(e: ForgeScreen.KeyReleased.Post) {
        ScreenInputEvent.KEY_RELEASED_POST.event.onRelease(Minecraft.getInstance(), e.screen, e.keyCode, e.scanCode, e.modifiers)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun input(e: ForgeInput.MouseScrollingEvent) {
        if (InputEvent.MOUSE_SCROLL.event.onScroll(Minecraft.getInstance(), e.deltaX, e.deltaY).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun input(e: ForgeInput.MouseButton.Pre) {
        if (InputEvent.MOUSE_CLICK_PRE.event.onClicked(Minecraft.getInstance(), e.button, e.action, e.modifiers).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun input(e: ForgeInput.MouseButton.Post) {
        if (InputEvent.MOUSE_CLICK_POST.event.onClicked(Minecraft.getInstance(), e.button, e.action, e.modifiers).isFalse)
            e.isCanceled = true
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun input(e: ForgeInput.Key) {
        if (InputEvent.KEY_PRESS.event.onPress(Minecraft.getInstance(), e.key, e.scanCode, e.action, e.modifiers).isFalse)
            e.isCanceled = true
    }

    class ModEventHandler {
        @SubscribeEvent(priority = EventPriority.HIGH)
        fun clientSetup(e: FMLClientSetupEvent) {
            ClientLifecycleEvent.CLIENT_SETUP.event.onChanged(Minecraft.getInstance())
        }

        @SubscribeEvent(priority = EventPriority.HIGH)
        fun shaderReg(e: RegisterShadersEvent) {
            ShaderReloadEvent.EVENT.event.reload(e.resourceProvider, e::registerShader)
        }
    }
}
