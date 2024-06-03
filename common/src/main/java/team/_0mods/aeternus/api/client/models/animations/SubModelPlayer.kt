/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.models.animations

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.animal.Pig
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.vehicle.Boat
import net.minecraft.world.entity.vehicle.Minecart
import net.minecraft.world.phys.Vec3
import team._0mods.aeternus.api.client.models.GltfModel
import team._0mods.aeternus.api.client.models.manager.LayerMode
import team._0mods.aeternus.api.client.models.manager.SubModel
import kotlin.math.pow
import kotlin.math.sqrt

object SubModelPlayer {
    fun update(model: GltfModel, capability: SubModel, currentTick: Int, partialTick: Float) {
        val layers = capability.layers

        model.animationPlayer.nodeModels.forEach { node ->
            node.clearTransform()
            val transform = node.transform.copy()
            layers.forEach {
                val animPose = it.computeTransform(node, model.animationPlayer.nameToAnimationMap, currentTick, partialTick)

                if (animPose != null) {
                    when (it.layerMode) {
                        LayerMode.ADD -> transform.add(animPose)
                        LayerMode.OVERWRITE -> {
                            node.clearTransform()
                            transform.set(node.fromLocal(animPose))
                        }
                    }
                }
            }
            node.transform.set(transform)
        }

        layers.removeIf { it.isEnd(currentTick, partialTick) }
    }
}