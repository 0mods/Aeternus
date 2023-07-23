package team.zeds.ancientmagic.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.registries.ForgeRegistries
import team.zeds.ancientmagic.api.block.InventoriedBlockEntity
import team.zeds.ancientmagic.api.handler.HandleStack
import team.zeds.ancientmagic.api.lazy.StructurePosition
import team.zeds.ancientmagic.init.registries.AMRegister
import team.zeds.ancientmagic.init.registries.AMTags

class AltarBlockEntity(pos: BlockPos, state: BlockState) : InventoriedBlockEntity(AMRegister.ALTAR_BLOCK_ENTITY.get(), pos, state) {
    @JvmField val inv: HandleStack

    //Structure validating
    var structureIsValid = false
    //Block validating
    var pedestalIsValid = false
    var bricksIsValid = false
    var bricksWallIsValid = false
    var cutWoodIsValid = false
    var fireStoneIsValid = false

    init {
        inv = this.createHandler(this::changeX)
    }

    private val pedestalPositions = StructurePosition.builder()
        .pos(2, 0, -2).pos(-2, 0, 2).pos(-2, 0, -2).pos(2, 0, 2)
        .pos(3, 1, 0).pos(-3, 1, 0).pos(0, 1, 3).pos(0, 1, -3)
        .build()
    private val bricksPosition = StructurePosition.builder()
        .pos(0, -1, 0).pos(1, -1, 0).pos(2, -1, 0).pos(3, -1, 0)
        .pos(-1, -1, 0).pos(-2, -1, 0).pos(-3, -1, 0).pos(0, -1, 1)
        .pos(0, -1, 2).pos(0, -1, 3).pos(0, -1, -1).pos(0, -1, -2)
        .pos(0, -1, -3)
        .build()
    private val bricksWallPosition = StructurePosition.builder()
        .pos(3, 0, 0).pos(-3, 0, 0).pos(0, 0, 3).pos(0, 0, -3)
        .build()
    private val cutWoodPosition = StructurePosition.builder()
        .pos(1, -1, 1).pos(1, -1, 2).pos(1, -1, -1).pos(1, -1, -2)
        .pos(2, -1, 1).pos(2, -1, 2).pos(2, -1, -1).pos(2, -1, -2)
        .pos(-1, -1, 1).pos(-1, -1, 2).pos(-1, -1, -1).pos(-1, -1, -2)
        .build()
    private val fireStonePosition = StructurePosition.builder()
        .pos(3, -1, 1).pos(3, -1, 2).pos(3, -1, -1).pos(3, -1, -2)
        .pos(-3, -1, 1).pos(-3, -1, 2).pos(-3, -1, -1).pos(-3, -1, -2)
        .pos(1, -1, 3).pos(2, -1, 3).pos(-1, -1, 3).pos(-2, -1, 3)
        .pos(1, -1, -3).pos(2, -1, -3).pos(-1, -1, -3).pos(-2, -1, -3)
        .build()
    fun getPedestalPosition(): MutableList<BlockPos> = this.pedestalPositions.get(this.blockPos)
    fun getBricksPosition(): MutableList<BlockPos> = this.bricksPosition.get(this.blockPos)
    fun getBricksWallPosition(): MutableList<BlockPos> = this.bricksWallPosition.get(this.blockPos)
    fun getCutWoodPosition(): MutableList<BlockPos> = this.cutWoodPosition.get(this.blockPos)
    fun fireStonePosition(): MutableList<BlockPos> = this.fireStonePosition.get(this.blockPos)

    companion object {
        @JvmStatic
        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: AltarBlockEntity) {
            val pedestals = blockEntity.getPedestalPosition()
            val bricks = blockEntity.getBricksPosition()
            val bricksWalls = blockEntity.getBricksWallPosition()
            val cutWoods = blockEntity.getCutWoodPosition()
            val fireStones = blockEntity.fireStonePosition()

            for (i in 0 .. pedestals.size) {
                val pedestalPos = pedestals[i]
                val blockAtState = level.getBlockState(pedestalPos).block

                blockEntity.pedestalIsValid = blockAtState == AMRegister.ALTAR_PEDESTAL_BLOCK.get()
            }
            for (i in 0 .. bricks.size) {
                val brickPos = bricks[i]
                val blockAtState = level.getBlockState(brickPos)

                blockEntity.bricksIsValid = blockAtState == Blocks.STONE_BRICKS
            }
            for (i in 0 .. bricksWalls.size) {
                val brickWallPos = bricksWalls[i]
                val blockAtState = level.getBlockState(brickWallPos)

                blockEntity.bricksWallIsValid = blockAtState == Blocks.STONE_BRICK_WALL
            }
            for (i in 0 .. cutWoods.size) {
                val cutWoodPos = cutWoods[i]
                val blockAtState = level.getBlockState(cutWoodPos)
                ForgeRegistries.BLOCKS.tags()!!.getTag(AMTags.instance!!.strippedWood).forEach { block ->
                    blockEntity.cutWoodIsValid = blockAtState == block
                }
            }
            for (i in 0 .. fireStones.size) {
                val fireStonePos = fireStones[i]
                val blockAtState = level.getBlockState(fireStonePos)
                blockEntity.fireStoneIsValid = blockAtState == Blocks.SMOOTH_STONE
            }
            blockEntity.structureIsValid = blockEntity.pedestalIsValid && blockEntity.bricksIsValid
                    && blockEntity.bricksWallIsValid && blockEntity.cutWoodIsValid && blockEntity.fireStoneIsValid
        }
    }

    override fun getInv(): HandleStack = this.inv

    fun createHandler(contentChanged: Runnable): HandleStack = HandleStack.create(2, contentChanged) { builder ->
        builder.setDefaultSlotLimit(1)
        builder.setCanExtract { builder.getStackInSlot(1).isEmpty }
        builder.setOutputSlots(1)
    }
}
