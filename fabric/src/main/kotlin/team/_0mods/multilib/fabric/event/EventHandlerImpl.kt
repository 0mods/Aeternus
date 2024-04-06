/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.multilib.fabric.event

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.event.player.UseItemCallback
import team._0mods.multilib.event.base.client.ClientLifecycleEvent
import team._0mods.multilib.event.base.client.ClientTickEvent
import team._0mods.multilib.event.base.client.ScreenEvent
import team._0mods.multilib.event.base.client.TooltipEvent
import team._0mods.multilib.event.base.common.*
import team._0mods.multilib.event.core.EventHandler

internal object EventHandlerImpl: EventHandler() {
    override fun registerClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(ClientLifecycleEvent.CLIENT_STARTED.event::onChanged)
        ClientLifecycleEvents.CLIENT_STOPPING.register(ClientLifecycleEvent.CLIENT_STOPPING.event::onChanged)

        ClientTickEvents.START_CLIENT_TICK.register(ClientTickEvent.CLIENT_PRE.event::tick)
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvent.CLIENT_POST.event::tick)
        ClientTickEvents.START_WORLD_TICK.register(ClientTickEvent.LEVEL_PRE.event::tick)
        ClientTickEvents.END_WORLD_TICK.register(ClientTickEvent.LEVEL_POST.event::tick)

        ItemTooltipCallback.EVENT.register(TooltipEvent.ITEM.event::append)

        HudRenderCallback.EVENT.register(ScreenEvent.RENDER_HUD.event::render)
    }

    override fun registerCommon() {
        ServerLifecycleEvents.SERVER_STARTING.register(LifecycleEvent.SERVER_STARTING.event::onChanged)
        ServerLifecycleEvents.SERVER_STARTED.register(LifecycleEvent.SERVER_STARTED.event::onChanged)
        ServerLifecycleEvents.SERVER_STOPPING.register(LifecycleEvent.SERVER_STOPPING.event::onChanged)
        ServerLifecycleEvents.SERVER_STOPPED.register(LifecycleEvent.SERVER_STOPPED.event::onChanged)

        ServerTickEvents.START_SERVER_TICK.register(TickEvent.SERVER_PRE.event::tick)
        ServerTickEvents.END_SERVER_TICK.register(TickEvent.SERVER_POST.event::tick)
        ServerTickEvents.START_WORLD_TICK.register(TickEvent.SERVER_LEVEL_PRE.event::tick)
        ServerTickEvents.END_WORLD_TICK.register(TickEvent.SERVER_LEVEL_POST.event::tick)

        ServerWorldEvents.LOAD.register { _, level -> LifecycleEvent.SERVER_LEVEL_LOAD.event.onChanged(level) }
        ServerWorldEvents.UNLOAD.register { _, level -> LifecycleEvent.SERVER_LEVEL_UNLOAD.event.onChanged(level) }

        CommandRegistrationCallback.EVENT.register(CommandRegistrationEvent.EVENT.event::register)

        UseItemCallback.EVENT.register { player, _, hand -> InteractionEvent.RIGHT_CLICK_ITEM.event.click(player, hand).asInteractionResult }
        UseBlockCallback.EVENT.register { player, _, hand, hit -> InteractionEvent.RIGHT_CLICK_BLOCK.event.click(player, hand, hit.blockPos, hit.direction).asInteractionResult }
        AttackBlockCallback.EVENT.register { player, _, hand, pos, dir -> InteractionEvent.LEFT_CLICK_BLOCK.event.click(player, hand, pos, dir).asInteractionResult }
        AttackEntityCallback.EVENT.register { player, level, hand, entity, hit -> PlayerEvent.ATTACK_ENTITY.event.attack(player, level, entity, hand, hit).asInteractionResult }
    }
}
