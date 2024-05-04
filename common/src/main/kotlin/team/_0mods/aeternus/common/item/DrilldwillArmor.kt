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
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem
import team._0mods.aeternus.api.item.ArmorMaterialCreation
import team._0mods.aeternus.api.item.ArmorMaterialCreation.Companion.builder
import team._0mods.aeternus.api.util.aRl
import team._0mods.aeternus.api.util.sec
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

class DrilldwillArmor(type: Type, properties: Properties) : ArmorItem(material, type, properties) {
    private var runTime = 0

    companion object {
        private val material = ArmorMaterialCreation.builder("drilldwill".aRl)
            .fullDef(3, 8, 6, 3)
            .durability(33)
            .ingredient(AeternusRegsitry.drilldwill)
            .knockback(0.5F)
            .toughness(3.5F)
            .build
    }

    private fun tick() {
        val slots = listOf(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)

        TickEvent.PLAYER_PRE.register { player ->
            val level = player.level()

            if (!level.isClientSide) {
                if (!player.checkEquipedArmor()) return@register

                val dmv = player.deltaMovement

                if (player.isSprinting && (dmv.x > 0 && dmv.z > 0)) runTime++
                else runTime = 0

                if (runTime >= 10.sec) {
                    val server = level.server ?: throw NullPointerException("Server is null on server side? What?")
                    val world = server.levelKeys().find { it.location() == "altake".aRl } ?: throw NullPointerException("Altake dimension is not found!")
                    val altakeLevel = server.getLevel(world) ?: throw NullPointerException("Altake dimension can't be loaded")
                    if (player.level() != altakeLevel) {
                        player.changeDimension(altakeLevel)
                        slots.forEach(player::broadcastBreakEvent)
                    }
                }
            }
        }
    }

    private fun Player.checkEquipedArmor(): Boolean {
        val head = this.getItemBySlot(EquipmentSlot.HEAD).item
        val chest = this.getItemBySlot(EquipmentSlot.CHEST).item
        val legs = this.getItemBySlot(EquipmentSlot.LEGS).item
        val feet = this.getItemBySlot(EquipmentSlot.FEET).item
        return head is DrilldwillArmor && chest is DrilldwillArmor && legs is DrilldwillArmor && feet is DrilldwillArmor
    }
}
