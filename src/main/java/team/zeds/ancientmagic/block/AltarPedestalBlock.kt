package team.zeds.ancientmagic.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import team.zeds.ancientmagic.api.lazy.VoxelShapeBuilder

class AltarPedestalBlock: Block(Properties.copy(Blocks.STONE)) {
    init {
        this.registerDefaultState(this.stateDefinition.any())
    }
    private val voxelModel = VoxelShapeBuilder.builder()
        .cube(4.0, 0.0, 4.1, 12.0, 2.0, 12.1)
        .cube(4.5, 2.0, 4.7, 11.4, 2.7, 11.5)
        .cube(4.5, 13.1, 4.5, 11.5, 13.8, 11.5)
        .cube(5.6, 2.0, 5.5, 10.6, 13.1, 10.5)
        .cube(4.0, 13.7, 4.0, 12.0, 15.7, 12.0)
        .cube(4.625, 15.7, 4.6, 11.375, 17.0, 11.4)
        .cube(7.0, 13.700000000000006, 1.9999999999999996, 9.0, 18.799999999999972, 4.000000000000002)
        .cube(11.999999999999998, 13.699999999999976, 7.0, 13.999999999999998, 18.800000000000093, 9.0)
        .cube(7.0, 13.699999999999973, 12.000000000000009, 9.0, 18.800000000000107, 14.000000000000009)
        .cube(2.000000000000002, 13.699999999999998, 7.0, 3.9999999999999916, 18.800000000000008, 9.0)
        .cube(7.0, 18.800000000000114, 3.1000000000000014, 9.0, 20.800000000000114, 5.0999999999999925)
        .cube(7.0, 18.800000000000114, 11.02500000000001, 9.0, 20.800000000000114, 13.02500000000001)
        .cube(3.0000000000000018, 18.800000000000114, 7.0, 4.999999999999991, 20.800000000000114, 9.0)
        .cube(10.975000000000009, 18.800000000000114, 7.0, 12.975000000000009, 20.800000000000114, 9.0)
        .of()

    @Deprecated("Deprecated in Java", ReplaceWith("voxelModel"))
    override fun getShape(
        state: BlockState,
        getter: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return voxelModel
    }
}
