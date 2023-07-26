package team.zeds.ancientmagic.api.helper

import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
/**
 * Port by AlgorithmLX
 * @author BlakeBr0
 */
class VoxelShapeBuilder {
    private var leftShape: VoxelShape? = null
    private var lastOrShape: VoxelShape? = null

    companion object {
        fun builder(): VoxelShapeBuilder {
            return VoxelShapeBuilder()
        }

        fun from(vararg shapes: VoxelShape): VoxelShapeBuilder {
            val builder = VoxelShapeBuilder()
            for (shape in shapes) builder.shape(shape)
            return builder
        }
    }

    fun shape(shape: VoxelShape): VoxelShapeBuilder {
        if (leftShape == null) {
            leftShape = shape
        } else {
            val newShape = Shapes.or(leftShape!!, shape)
            lastOrShape = if (lastOrShape != null) {
                Shapes.or(lastOrShape!!, newShape)
            } else {
                newShape
            }
            leftShape = null
        }
        return this
    }

    fun cube(xMin: Double, yMin: Double, zMin: Double, xMax: Double, yMax: Double, zMax: Double): VoxelShapeBuilder {
        val shape = Block.box(xMin, yMin, zMin, xMax, yMax, zMax)
        return shape(shape)
    }

    fun of(): VoxelShape = this.lastOrShape!!
}