package team.zeds.ancientmagic.api.lazy

import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
/**
 * Port by AlgorithmLX
 * @author BlakeBr0
 */
class VoxelShapeBuilder {
    private var `is`: VoxelShape? = null
    private var last: VoxelShape? = null

    companion object {
        @JvmStatic
        fun from(vararg shapes: VoxelShape) : VoxelShapeBuilder {
            val builder = VoxelShapeBuilder()
            for (shape in shapes) builder.`is`

            return builder
        }
    }

    fun shape(shape: VoxelShape): VoxelShapeBuilder {
        if (this.`is` == null) this.`is` = shape
        else {
            val newShape = this.`is`?.let { Shapes.or(it, shape) }!!

            if (this.last != null) this.last = this.last?.let { Shapes.or(it, newShape) }
            else this.last = newShape

            this.`is` = null
        }

        return this
    }

    fun cube(xMin: Double, yMin: Double, zMin: Double, xMax: Double, yMax: Double, zMax: Double): VoxelShapeBuilder {
        val shape = Block.box(xMin, yMin, zMin, xMax, yMax, zMax)
        return this.shape(shape)
    }

    fun of(): VoxelShape = this.last!!
}