/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.common

import net.minecraft.advancements.AdvancementHolder
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult
import team._0mods.aeternus.api.event.result.EventResultHolder

interface PlayerEvent {
    companion object {
        @JvmField val PLAYER_JOIN: Event<PlayerJoin> = EventFactory.createNoResult()

        @JvmField val PLAYER_QUIT: Event<PlayerQuit> = EventFactory.createNoResult()

        @JvmField val PLAYER_RESPAWN: Event<PlayerRespawn> = EventFactory.createNoResult()

        @JvmField val PLAYER_ADVANCEMENT: Event<PlayerAdvancement> = EventFactory.createNoResult()

        @JvmField val PLAYER_CLONE: Event<PlayerClone> = EventFactory.createNoResult()

        @JvmField val CRAFT_ITEM: Event<CraftItem> = EventFactory.createNoResult()

        @JvmField val SMELT_ITEM: Event<SmeltItem> = EventFactory.createNoResult()
        
        @JvmField val PICKUP_ITEM_PRE: Event<PickupItemPredicate> = EventFactory.createEventResult()

        @JvmField val PICKUP_ITEM_POST: Event<PickupItem> = EventFactory.createNoResult()

        @JvmField val CHANGE_DIMENSION: Event<ChangeDimension> = EventFactory.createNoResult()

        @JvmField val DROP_ITEM: Event<DropItem> = EventFactory.createEventResult()

        @JvmField val OPEN_MENU: Event<OpenMenu> = EventFactory.createNoResult()

        @JvmField val CLOSE_MENU: Event<CloseMenu> = EventFactory.createNoResult()

        @JvmField val FILL_BUCKET: Event<FillBucket> = EventFactory.createEventResultHolder()

        @JvmField val ATTACK_ENTITY: Event<AttackEntity> = EventFactory.createEventResult()
    }

    fun interface PlayerJoin {
        fun join(player: ServerPlayer)
    }

    fun interface PlayerQuit {
        fun quit(player: ServerPlayer)
    }

    fun interface PlayerRespawn {
        fun respawn(newPlayer: ServerPlayer, conqueredEnd: Boolean)
    }

    fun interface PlayerClone {
        fun clone(oldPlayer: ServerPlayer, newPlayer: ServerPlayer, endLeave: Boolean)
    }

    fun interface PlayerAdvancement {
        fun award(player: ServerPlayer, holder: AdvancementHolder)
    }

    fun interface CraftItem {
        fun craft(player: Player, result: ItemStack, container: Container)
    }

    fun interface SmeltItem {
        fun smelt(player: Player, result: ItemStack)
    }

    fun interface PickupItemPredicate {
        fun canPickup(player: Player, entity: ItemEntity, stack: ItemStack): EventResult
    }

    fun interface PickupItem {
        fun pickup(player: Player, entity: ItemEntity, stack: ItemStack)
    }

    fun interface ChangeDimension {
        fun change(player: ServerPlayer, oldLevel: ResourceKey<Level>, newLevel: ResourceKey<Level>)
    }

    fun interface DropItem {
        fun drop(player: Player, entity: ItemEntity): EventResult
    }

    fun interface OpenMenu {
        fun open(player: Player, menu: AbstractContainerMenu)
    }

    fun interface CloseMenu {
        fun close(player: Player, menu: AbstractContainerMenu)
    }

    fun interface FillBucket {
        fun fill(player: Player, level: Level, stack: ItemStack, target: HitResult?): EventResultHolder<ItemStack>
    }

    fun interface AttackEntity {
        fun attack(player: Player, level: Level, target: Entity, hand: InteractionHand, result: EntityHitResult?): EventResult
    }
}
