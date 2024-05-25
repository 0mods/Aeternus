/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.item

import dev.architectury.event.events.common.TickEvent
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import team._0mods.aeternus.api.item.ArmorMaterialCreation
import team._0mods.aeternus.api.item.ArmorMaterialCreation.Companion.builder
import team._0mods.aeternus.api.text.TranslationBuilder
import team._0mods.aeternus.api.util.*
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

class DrilldwillArmor(type: Type, properties: Properties) : ArmorItem(material, type, properties) {
    private var runTime = 0
    private var isTeleportedOrInDim = false

    init {
        tick()
    }

    companion object {
        private val material = ArmorMaterialCreation.builder("drilldwill".aRl)
            .fullDef(3, 8, 6, 3)
            .durability(33)
            .ingredient(AeternusRegsitry.drilldwill.get())
            .knockback(0.5F)
            .toughness(3.5F)
            .build
    }

    override fun getName(stack: ItemStack): Component = type.generateArmorTranslateByParent(AeternusRegsitry.drilldwill.get())

    private fun tick() {
        val slots = listOf(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)

        TickEvent.PLAYER_PRE.register { player ->
            val level = player.level()
            val playerIsMoving = player.isSprinting && player.isMoving && !player.isJumping && !player.isFalling

            if (!level.isClientSide) {
                if (player !is ServerPlayer) return@register

                if (!player.checkEquippedArmor()) return@register

                if (playerIsMoving && player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) runTime++
                else runTime = 0

                if (runTime >= 10.sec) {
                    val isOnIter = player.isOnIter
                    val onIter = isOnIter.first
                    val iterLevel = isOnIter.second
                    if (onIter) {
                        player.changeDimension(iterLevel)
                        slots.forEach(player::broadcastBreakEvent)
                    }
                }
            } else {
                // Here logic of shader rendering at player's screen
            }
        }
    }

    private fun Player.checkEquippedArmor(): Boolean {
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
