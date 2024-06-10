/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.network

import kotlinx.serialization.Serializable
import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.client.models.animations.AnimationState
import team._0mods.aeternus.api.client.models.animations.PlayMode
import team._0mods.aeternus.api.client.models.manager.AnimatedEntityCapability
import team._0mods.aeternus.api.client.models.manager.AnimationLayer
import team._0mods.aeternus.api.client.models.manager.IAnimated
import team._0mods.aeternus.api.client.models.manager.LayerMode
import team._0mods.aeternus.api.packets.AeternusPacket
import team._0mods.aeternus.api.packets.AeternusPacketInstance
import team._0mods.aeternus.api.util.get

@AeternusPacket
@Serializable
class StartAnimationPacket(
    private val entityId: Int,
    private val name: String,
    private val layerMode: LayerMode,
    private val playType: PlayMode,
    private val speed: Float = 1.0f,
) : AeternusPacketInstance<StartAnimationPacket> {
    override fun handle(player: Player) {
        player.level().getEntity(entityId)?.let { entity ->
            if (entity is IAnimated || entity is Player) {
                val capability = entity[AnimatedEntityCapability::class]
                if (capability.layers.any { it.animation == name }) return@let

                capability.layers += AnimationLayer(
                    name,
                    layerMode,
                    playType,
                    speed,
                    0
                )
            }
        }
    }
}

@AeternusPacket
@Serializable
class StopAnimationPacket(
    private val entityId: Int,
    val name: String,
) : AeternusPacketInstance<StopAnimationPacket> {
    override fun handle(player: Player) {
        player.level().getEntity(entityId)?.let { entity ->
            if (entity is IAnimated || entity is Player) {
                val capability = entity[AnimatedEntityCapability::class]

                capability.layers.filter { it.animation == name }.forEach {
                    it.state = AnimationState.FINISHED
                }
            }
        }
    }
}
