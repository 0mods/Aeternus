/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.platformredirect.common.item

import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.portal.DimensionTransition
import ru.hollowhorizon.hc.common.events.EventBus
import ru.hollowhorizon.hc.common.events.tick.TickEvent
import team._0mods.aeternus.api.item.ITabbed
import team._0mods.aeternus.api.util.*
import team._0mods.aeternus.platformredirect.api.item.ArmorMaterialCreation
import team._0mods.aeternus.platformredirect.api.item.ArmorMaterialCreation.Companion.builder
import team._0mods.aeternus.platformredirect.api.util.aRl
import team._0mods.aeternus.platformredirect.api.util.generateArmorTranslateByParent
import team._0mods.aeternus.platformredirect.api.util.isFalling
import team._0mods.aeternus.platformredirect.api.util.isMoving
import team._0mods.aeternus.platformredirect.common.init.registry.AeternusRegsitry

class DrilldwillArmor(type: Type, properties: Properties) : ArmorItem(material, type, properties), ITabbed {
    private var runTime = 0
    private var isTeleportedOrInDim = false

    init {
        EventBus.register(this::onPlayerTick)
    }

    companion object {
        private val material = ArmorMaterialCreation.builder("drilldwill".aRl)
            .fullDef(3, 8, 6, 3)
            .ingredient(AeternusRegsitry.drilldwill.get())
            .knockback(0.5F)
            .toughness(3.5F)
            .build
    }

    override fun getName(stack: ItemStack): Component = type.generateArmorTranslateByParent(AeternusRegsitry.drilldwill.get())

    fun onPlayerTick(e: TickEvent.Entity) {
        val slots = listOf(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
        val player = e.entity
        if (player is Player) {
            val level = player.level()
            if (!level.isClientSide) {
                val isMoving = player.isSprinting && player.isMoving && !player.isFalling
                if (player.checkEquippedArmor) {
                    if (isMoving && player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) runTime++
                    else runTime = 0

                    if (runTime >= 10.sec) {
                        if (isTeleportedOrInDim) return
                        val isOnIter = (player as ServerPlayer).isOnIter
                        val onIter = isOnIter.first
                        val iterLevel = isOnIter.second
                        if (onIter) {
                            player.changeDimension(DimensionTransition(iterLevel, player) {
                                slots.forEach {
                                    player.onEquippedItemBroken(player.getItemBySlot(it).item, it)
                                }
                            })
                        }
                    }
                }
            } else {
                // Here logic of shader rendering at player's screen
            }
        }
    }

    private val Player.checkEquippedArmor: Boolean get() {
        val head = this.getItemBySlot(EquipmentSlot.HEAD).item
        val chest = this.getItemBySlot(EquipmentSlot.CHEST).item
        val legs = this.getItemBySlot(EquipmentSlot.LEGS).item
        val feet = this.getItemBySlot(EquipmentSlot.FEET).item
        return head is DrilldwillArmor && chest is DrilldwillArmor && legs is DrilldwillArmor && feet is DrilldwillArmor
    }

    private val ServerPlayer.isOnIter: Pair<Boolean, ServerLevel>
        get() {
            val server = this.server
            val levelKey = server.levelKeys().find { it.location() == "iter".aRl } ?: throw NullPointerException("Iter dimension is not found!")
            val level = server.getLevel(levelKey) ?: throw NullPointerException("Iter dimension can't be loaded")
            return (this.level() == level) to level
        }
}
