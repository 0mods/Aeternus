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
import dev.architectury.event.events.common.InteractionEvent
import net.minecraft.network.chat.Component
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.pack.PackMetadataSection
import net.minecraft.server.packs.repository.Pack
import net.minecraft.server.packs.repository.PackCompatibility
import net.minecraft.server.packs.repository.PackSource
import net.minecraft.util.InclusiveRange
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.flag.FeatureFlagSet
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import team._0mods.aeternus.api.client.AutomaticPackResources
import team._0mods.aeternus.api.event.AddPackEvent
import team._0mods.aeternus.common.ModName
import team._0mods.aeternus.common.init.registry.AeternusRegsitry
import team._0mods.aeternus.service.ServiceProvider
import java.util.*
import kotlin.random.Random

object AeternusEventsInit {
    private const val PLAYER_UUID_ITEM = "${ModName}PlayerCheckUUID"

    fun initServerEvents() {
        InteractionEvent.RIGHT_CLICK_ITEM.register { player, hand ->
            val level = player.level()
            if (hand == InteractionHand.MAIN_HAND) {
                val stack = player.getItemInHand(hand)
                if (stack.`is`(Items.BOOK)) {
                    val stackOfKNBook = ItemStack(AeternusRegsitry.knowledgeBook)
                    if (!player.inventory.contains(stackOfKNBook)) {
                        val etheriumCount = ServiceProvider.etheriumHelper.getCountForPlayer(player)
                        val random = Random(35)
                        if (etheriumCount >= 35) {
                            player.getItemInHand(hand).shrink(1)
                            stackOfKNBook.orCreateTag.putUUID(PLAYER_UUID_ITEM, player.uuid)
                            val droppedItem = ItemEntity(level, player.x, player.y, player.z, stackOfKNBook)
                            droppedItem.setNoPickUpDelay()
                            level.addFreshEntity(droppedItem)
                            ServiceProvider.etheriumHelper.consume(player, random.nextInt())
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

    fun initClientEvents() {
//        AddPackEvent.ASSETS.register { adder, creator ->
//            val automaticResources = AutomaticPackResources.packInstance()
//            adder.accept(creator.create(
//                automaticResources.packId(),
//                Component.literal(automaticResources.packId()),
//                true,
//                automaticResources.resourceSupplier(),
//                Pack.Info(
//                    Component.literal(automaticResources.packId()),
//                    PackCompatibility.TOO_NEW,
//                    FeatureFlagSet.of(),
//                    listOf()
//                ),
//                Pack.Position.TOP,
//                true,
//                PackSource.BUILT_IN
//            ))
//        }
    }
}
