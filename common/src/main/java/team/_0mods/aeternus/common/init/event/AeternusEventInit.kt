/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.init.event

import dev.architectury.event.CompoundEventResult
import dev.architectury.event.EventResult
import dev.architectury.event.events.client.ClientTickEvent
import dev.architectury.event.events.common.InteractionEvent
import dev.architectury.event.events.common.TickEvent
import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FireBlock
import team._0mods.aeternus.api.client.TickHandler
import team._0mods.aeternus.api.event.EntityHurtEvent
import team._0mods.aeternus.api.magic.research.ResearchRequired
import team._0mods.aeternus.client.screen.configScreen
import team._0mods.aeternus.client.keys.AeternusKeys
import team._0mods.aeternus.common.ModName
import team._0mods.aeternus.common.init.registry.AeternusRegsitry
import team._0mods.aeternus.service.EtheriumHelper
import team._0mods.aeternus.service.ResearchHelper
import kotlin.random.Random

object AeternusEventsInit {
    internal const val PLAYER_UUID_ITEM = "${ModName}PlayerCheckUUID"
    internal val itemBurn = mutableMapOf<ItemStack, ItemStack>()

    fun initServerEvents() {
        onUse()
        hurtItem()
        serverTickHandler()
    }

    fun initClientEvents() {
        clientTickHandler()
        buttonClickHandler()
    }

    private fun onUse() {
        InteractionEvent.RIGHT_CLICK_ITEM.register { player, hand ->
            val level = player.level()
            if (hand == InteractionHand.MAIN_HAND) {
                val stack = player.getItemInHand(hand)
                if (stack.`is`(Items.BOOK)) {
                    val stackOfKNBook = ItemStack(AeternusRegsitry.knowledgeBook.get())
                    if (!player.inventory.contains(stackOfKNBook)) {
                        val etheriumCount = EtheriumHelper.getCountForPlayer(player)
                        val random = Random(35)
                        if (etheriumCount >= 35) {
                            player.getItemInHand(hand).shrink(1)
                            stackOfKNBook.orCreateTag.putUUID(PLAYER_UUID_ITEM, player.uuid)
                            val droppedItem = ItemEntity(level, player.x, player.y, player.z, stackOfKNBook)
                            droppedItem.setNoPickUpDelay()
                            level.addFreshEntity(droppedItem)
                            EtheriumHelper.consume(player, random.nextInt())
                            return@register CompoundEventResult.interruptTrue(stack)
                        } else {
                            return@register CompoundEventResult.pass()
                        }
                    }
                }
            }
            return@register CompoundEventResult.pass()
        }
    }

    private fun hurtItem() {
        EntityHurtEvent.EVENT.register { entity, _, _ ->
            val level = entity.level()
            if (level.isClientSide) return@register EventResult.interruptFalse()
            if (entity is ItemEntity) {
                if (!entity.isAlive && entity.type == EntityType.ITEM && entity.isOnFire) {
                    if (itemBurn.isEmpty()) return@register EventResult.interruptFalse()

                    itemBurn.entries.forEach {
                        val ingredient = it.key
                        val result = it.value

                        if (ingredient.`is`(entity.item.item)) {
                            val e = ItemEntity(level, entity.x, entity.y + 1, entity.z, result).apply {
                                isInvulnerable = true
                                setNoPickUpDelay()
                            }

                            level.addFreshEntity(e)
                        }
                    }

                    val block = level.getBlockState(entity.blockPosition()).block
                    if (block is FireBlock || block == Blocks.LAVA) {
                        level.setBlock(entity.blockPosition(), Blocks.AIR.defaultBlockState(), 1)
                    }

                    return@register EventResult.interruptTrue()
                }
            }

            return@register EventResult.pass()
        }
    }

    private fun playerResearchCheck() {
        TickEvent.PLAYER_PRE.register {
            val itemStack = it.getItemInHand(InteractionHand.MAIN_HAND)
            val item = itemStack.item

            if (item is ResearchRequired) {
                val req = item.requirements

                if (!ResearchHelper.hasResearches(it, *req.toTypedArray())) {
                    item.lockItem = true
                }
            }
        }
    }

    private fun serverTickHandler() {
        TickEvent.SERVER_POST.register {
            TickHandler.serverTicks()
        }
    }

    private fun clientTickHandler() {
        ClientTickEvent.CLIENT_POST.register {
            TickHandler.serverTicks()
        }
    }

    private fun buttonClickHandler() {
        ClientTickEvent.CLIENT_POST.register {
            while (AeternusKeys.configGUIOpen.consumeClick()) {
                Minecraft.getInstance().setScreen(configScreen())
            }
        }
    }
}
