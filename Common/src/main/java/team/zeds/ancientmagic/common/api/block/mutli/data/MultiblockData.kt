package team.zeds.ancientmagic.common.api.block.mutli.data

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.*
import net.minecraft.core.Vec3i
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.Block
import org.joml.Vector3d

object MultiBlockStorage {
    val multiBlockActions: HashMap<String, (Player, BlockPos) -> Unit> = HashMap()
}

private fun Vec3i.northI(): Vec3i {
    return Vec3i(this.x, this.y, -this.z)
}

private fun Vec3i.southI(): Vec3i {
    return Vec3i(-this.x, this.y, this.z)
}

private operator fun Vec3i.unaryMinus(): Vec3i {
    return Vec3i(-this.x, -this.y, -this.x)
}

class MultiblockConfig {
    var modelName = ""
    var offset = Vector3d(0.0, 0.0, 0.0)
    var onOpen: (Player, BlockPos) -> Unit = { _, _ -> }
}

class MultiblockLayer(val yIndex: Int) {
    val layer = HashMap<BlockPos, Block>()
    var zIndex = 0

    fun line(vararg blocks: Block) {
        blocks.indices.forEach {
            layer[BlockPos(zIndex, yIndex, it)] = blocks[it]
        }
        zIndex++
    }
}

class OffsetMultiBlockPos(val maxX: Int, val maxY: Int, val maxZ: Int, val direction: Direction) {
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0

    fun next(): Boolean {
        when (direction) {
            EAST -> {
                x--
                if (-x > maxX) {
                    x = 0
                    y--
                    if (-y > maxY) {
                        y = 0
                        z--
                        if (-z > maxZ) {
                            return false
                        }
                    }
                }
                return true
            }

            WEST -> {
                x++
                if (x > maxX) {
                    x = 0
                    y--
                    if (-y > maxY) {
                        y = 0
                        z++
                        if (z > maxZ) {
                            return false
                        }
                    }
                }
                return true
            }

            NORTH -> {
                x--
                if (-x > maxZ) {
                    x = 0
                    y--
                    if (-y > maxY) {
                        y = 0
                        z++
                        if (z > maxX) {
                            return false
                        }
                    }
                }
                return true
            }

            SOUTH -> {
                x++
                if (x > maxZ) {
                    x = 0
                    y--
                    if (-y > maxY) {
                        y = 0
                        z--
                        if (-z > maxX) {
                            return false
                        }
                    }
                }
                return true
            }

            else -> return false
        }
    }

    fun pos(): Vec3i {
        return Vec3i(x, y, z)
    }
}