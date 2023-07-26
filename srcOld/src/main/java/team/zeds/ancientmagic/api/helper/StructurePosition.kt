package team.zeds.ancientmagic.api.helper

import net.minecraft.core.BlockPos

/**
 * @author BlakeBr0
 */
class StructurePosition(private val positions: MutableList<BlockPos>) {
    fun get(pos: BlockPos): MutableList<BlockPos> {
        return this.positions.stream().map(pos::offset).toList()
    }

    companion object {
        @JvmStatic
        fun of(positions: MutableList<BlockPos>) : StructurePosition {
            return StructurePosition(positions)
        }

        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }

    class Builder {
        val positions: MutableList<BlockPos> = mutableListOf()

        fun pos(x: Int, y: Int, z: Int): Builder {
            this.positions.add(BlockPos(x, y, z))
            return this
        }

        fun build(): StructurePosition {
            return StructurePosition(this.positions)
        }
    }
}