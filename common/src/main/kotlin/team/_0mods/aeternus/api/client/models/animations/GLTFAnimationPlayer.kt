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

import net.minecraft.client.gui.screens.Screen
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.client.models.GltfModel
import team._0mods.aeternus.api.client.models.GltfTree
import team._0mods.aeternus.api.client.models.Transformation
import team._0mods.aeternus.api.client.models.manager.AnimatedEntityCapability
import team._0mods.aeternus.api.client.models.manager.LayerMode
import team._0mods.aeternus.api.client.models.manager.Pose

open class GLTFAnimationPlayer(val model: GltfModel) {
    private val templates: HashMap<AnimationType, String> = AnimationType.load(model.modelTree)
    val nodeModels = model.modelTree.walkNodes()
    private var currentSpeed = 1f
    val nameToAnimationMap: Map<String, Animation> = model.modelTree.animations.associate {
        val name = it.name ?: "Unnamed"
        name to AnimationLoader.createAnimation(
            model.modelTree,
            name
        )!!
    }
    val typeToAnimationMap: Map<AnimationType, Animation> =
        templates.mapNotNull { it.key to (nameToAnimationMap[it.value] ?: return@mapNotNull null) }.toMap()
    var currentLoopAnimation = AnimationType.IDLE
    var currentTick = 0
    val head by lazy { nodeModels.filter(GltfTree.Node::isHead) }

    fun updateEntity(entity: LivingEntity, capability: AnimatedEntityCapability, partialTick: Float) {
        val switchRot = capability.switchHeadRot
        val modifier = if (entity is Player) 0.1f else 0.2f
        currentSpeed = entity.attributes.getValue(Attributes.MOVEMENT_SPEED).toFloat() / modifier
        if (entity.isShiftKeyDown) currentSpeed *= 0.6f
        head.forEach {
            val newRot = capability.headLayer.computeRotation(entity, switchRot, partialTick)
            it.transform.addRotationRight(newRot)
        }
    }

    fun update(capability: AnimatedEntityCapability, partialTick: Float) {
        val definedLayer = capability.definedLayer
        definedLayer.update(currentLoopAnimation, currentSpeed, currentTick, partialTick)
        val pose = capability.pose
        var rawPose = capability.rawPose

        if (pose != null) {
            if (pose.map.isEmpty()) rawPose?.shouldRemove = true
            else rawPose = Pose(capability.pose!!.map.mapNotNull {
                (model.modelTree.findNodeByIndex(it.key) ?: return@mapNotNull null) to it.value
            }.toMap().toMutableMap())
            capability.rawPose = rawPose
            capability.pose = null
        }

        rawPose?.update(currentTick, partialTick)

        val animationOverrides = typeToAnimationMap + capability.animations.mapNotNull {
            it.key to (nameToAnimationMap[it.value] ?: return@mapNotNull null)
        }.toMap()

        val layers = capability.layers
        nodeModels.forEach { node ->
            node.clearTransform()
            val transform = node.transform.copy()
            definedLayer.computeTransform(node, animationOverrides, currentSpeed, currentTick, partialTick)
                ?.let { animPose ->
                    transform.set(node.fromLocal(animPose))
                }
            node.transform.set(transform)
            layers.forEach {
                val animPose = it.computeTransform(node, nameToAnimationMap, currentTick, partialTick)

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
            rawPose?.let { transform.add(it.computeTransform(node) ?: Transformation(), false) }
            node.transform.set(transform)
        }

        if(rawPose?.canRemove == true) capability.rawPose = null

        layers.removeIf { it.isEnd(currentTick, partialTick) }
    }

    fun setTick(tick: Int) {
        this.currentTick = tick
    }
}
