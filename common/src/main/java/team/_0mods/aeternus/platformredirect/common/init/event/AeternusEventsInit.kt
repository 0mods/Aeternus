/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.platformredirect.common.init.event

import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import ru.hollowhorizon.hc.common.events.SubscribeEvent
import ru.hollowhorizon.hc.common.events.registry.RegisterReloadListenersEvent
import ru.hollowhorizon.hc.common.events.tick.TickEvent
import team._0mods.aeternus.api.magic.research.ResearchRequired
import team._0mods.aeternus.platformredirect.api.magic.research.ResearchReloadListener
import team._0mods.aeternus.platformredirect.common.init.AeternusCorePlugin
import team._0mods.aeternus.service.ResearchHelper

object AeternusEventsInit {
    internal val itemBurn = mutableMapOf<ItemStack, ItemStack>()

    private fun onUse() {
        /*InteractionEvent.RIGHT_CLICK_ITEM.register { player, hand ->
            val level = player.level()
            if (hand == InteractionHand.MAIN_HAND) {
                val stack = player.getItemInHand(hand)
                if (stack.`is`(Items.BOOK)) {
                    val stackOfKNBook = ItemStack(AeternusRegsitry.knowledgeBook.get())
                    val stackAsItem = stackOfKNBook.item as KnowledgeBook
                    if (!player.inventory.contains(stackOfKNBook)) {
                        val etheriumCount = EtheriumHelper.getCountForPlayer(player)
                        val random = Random(35)
                        if (etheriumCount >= 35) {
                            player.getItemInHand(hand).shrink(1)
                            stackAsItem.playerUUID = player.uuid
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
        }*/
    }

    private fun hurtItem() {
        /*EntityHurtEvent.EVENT.register { entity, _, _ ->
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
        }*/
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerResearchCheck(e: TickEvent.Entity) {
        val player = e.entity
        if (player is Player) {
            val stack = player.getItemInHand(InteractionHand.MAIN_HAND)
            val item = stack.item

            if (item is ResearchRequired) {
                val req = item.requirements

                if (!ResearchHelper.hasResearches(player, *req.toTypedArray()))
                    item.lockItem = true
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onRegisterReloadListener(e: RegisterReloadListenersEvent.Server) {
        e.register(ResearchReloadListener(AeternusCorePlugin.researchRegistry))
    }
}
