/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forge.api.event

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.ClientChatEvent as ForgeChat
import net.minecraftforge.client.event.ClientPlayerNetworkEvent
import net.minecraftforge.client.event.ContainerScreenEvent
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent
import net.minecraftforge.client.event.RenderGuiEvent
import net.minecraftforge.event.TickEvent as ForgeTick
import net.minecraftforge.client.event.ScreenEvent as ForgeScreen
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.level.LevelEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import team._0mods.aeternus.api.event.base.client.*
import team._0mods.aeternus.common.impl.screen.ScreenAccessImpl

object ClientEventsHandler {
    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun tooltip(e: ItemTooltipEvent) {
        TooltipEvent.ITEM.event.append(e.itemStack, e.toolTip, e.flags)
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun tick(event: ForgeTick.ClientTickEvent) {
        if (event.phase == ForgeTick.Phase.START)
            ClientTickEvent.CLIENT_PRE.event.tick(Minecraft.getInstance())
        else if (event.phase == ForgeTick.Phase.END) ClientTickEvent.CLIENT_POST.event.tick(Minecraft.getInstance())
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun postRender(e: RenderGuiEvent.Post) {
        ScreenEvent.RENDER_GUI_OVERLAY.event.render(e.guiGraphics, e.partialTick)
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun login(e: ClientPlayerNetworkEvent.LoggingIn) {
        ClientPlayerEvent.PLAYER_JOIN.event.onJoin(e.player)
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun logout(e: ClientPlayerNetworkEvent.LoggingOut) {
        ClientPlayerEvent.PLAYER_LEAVE.event.onLeave(e.player)
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun clone(e: ClientPlayerNetworkEvent.Clone) {
        ClientPlayerEvent.PLAYER_CLONE.event.onClone(e.oldPlayer, e.newPlayer)
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun screenPre(e: ForgeScreen.Init.Pre) {
        if (ScreenEvent.INIT_PRE.event.init(e.screen, ScreenAccessImpl(e.screen)).isFalse) e.isCanceled = true
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun screenPost(e: ForgeScreen.Init.Post) {
        ScreenEvent.INIT_POST.event.init(e.screen, ScreenAccessImpl(e.screen))
    }
    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)

    fun debugTest(e: CustomizeGuiOverlayEvent.DebugText) {
        if (Minecraft.getInstance().gui.debugOverlay.showDebugScreen()) {
            ScreenEvent.DEBUG_LEFT.event.render(e.left)
            ScreenEvent.DEBUG_LEFT.event.render(e.right)
        }
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun chat(e: ForgeChat) {
        val process = ClientChatEvent.SEND.event.send(e.message, null)
        if (process.isFalse) e.isCanceled = true
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun chatReceived(e: ClientChatReceivedEvent) {
        val process = ClientChatEvent.RECEIVED.event.process(e.boundChatType, e.message)

        if (process.isPresent) {
            if (process.isFalse) e.isCanceled = true
            else if (process.obj != null) e.message = process.obj!!
        }
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun world(e: LevelEvent.Load) {
        if (e.level.isClientSide) {
            val world = e.level as ClientLevel
            ClientLifecycleEvent.CLIENT_LEVEL_LOAD.event.onChanged(world)
        }
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun open(e: ForgeScreen.Opening) {
        val result = ScreenEvent.SET_SCREEN.event.setScreen(e.screen)
        if (result.isPresent) {
            if (result.isFalse) e.isCanceled = true
            else if (result.obj != null) e.newScreen = result.obj!!
        }
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun renderPre(e: ForgeScreen.Render.Post) {
        ScreenEvent.RENDER_POST.event.render(e.screen, e.guiGraphics, e.mouseX, e.mouseY, e.partialTick)
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun background(e: ContainerScreenEvent.Render.Background) {
        ScreenEvent.RENDER_CONTAINER_BACKGROUND.event.render(e.containerScreen, e.guiGraphics, e.mouseX, e.mouseY, Minecraft.getInstance().deltaFrameTime)
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun foreground(e: ContainerScreenEvent.Render.Foreground) {
        ScreenEvent.RENDER_CONTAINER_FOREGROUND.event.render(e.containerScreen, e.guiGraphics, e.mouseX, e.mouseY, Minecraft.getInstance().deltaFrameTime)
    }

    fun playerRMC(e: PlayerInteractEvent.RightClickEmpty) {

    }
}
