package team.zeds.ancientmagic.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.api.block.InventoriedBlockEntity
import team.zeds.ancientmagic.api.handler.HandleStack
import team.zeds.ancientmagic.api.helper.StructurePosition
import team.zeds.ancientmagic.api.mod.AMConstant
import team.zeds.ancientmagic.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.init.registries.AMRegister
import team.zeds.ancientmagic.recipes.AltarRecipe

class AltarBlockEntity(pos: BlockPos, state: BlockState) : InventoriedBlockEntity(AMRegister.ALTAR_BLOCK_ENTITY.get(), pos, state) {
    val strippedWoods = mutableListOf<Block>(
        Blocks.STRIPPED_OAK_WOOD,
        Blocks.STRIPPED_SPRUCE_WOOD,
        Blocks.STRIPPED_BIRCH_WOOD,
        Blocks.STRIPPED_JUNGLE_WOOD,
        Blocks.STRIPPED_ACACIA_WOOD,
        Blocks.STRIPPED_CHERRY_WOOD,
        Blocks.STRIPPED_DARK_OAK_WOOD,
        Blocks.STRIPPED_MANGROVE_WOOD
    )
    private val inv: HandleStack
    private val recipeInv: HandleStack

    //Structure validating
    var structureIsValid = false
    //Block validating
    var pedestalIsValid = false
    var bricksIsValid = false
    var bricksWallIsValid = false
    var cutWoodIsValid = false
    var fireStoneIsValid = false
    //functional
    private var progress: Int = 0
    private var active: Boolean = false
    private var recipe: AltarRecipe? = null

    init {
        inv = this.createHandler(this::changeX)
        recipeInv = HandleStack.create(9)
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
        .pos(-2, -1, 1).pos(-2, -1, 2).pos(-2, -1, -1).pos(-2, -1, -2)
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

            for (i in 0 until pedestals.size) {
                val pedestalPos = pedestals[i]
                val blockAtState = level.getBlockState(pedestalPos).block

                blockEntity.pedestalIsValid = if (blockAtState == AMRegister.ALTAR_PEDESTAL_BLOCK.get() && blockEntity.pedestalIsValid) {
                    AMConstant.LOGGER.info("pedestals is valid")
                    true
                } else {
                    AMConstant.LOGGER.debug("pedestals isn't valid")
                    false
                }
            }
            for (i in 0 until bricks.size) {
                val brickPos = bricks[i]
                val blockAtState = level.getBlockState(brickPos)

                blockEntity.bricksIsValid = blockAtState == Blocks.STONE_BRICKS && blockEntity.bricksIsValid
            }
            for (i in 0 until bricksWalls.size) {
                val brickWallPos = bricksWalls[i]
                val blockAtState = level.getBlockState(brickWallPos)

                blockEntity.bricksWallIsValid = blockAtState == Blocks.STONE_BRICK_WALL && blockEntity.bricksWallIsValid
            }
            for (i in 0 until cutWoods.size) {
                val cutWoodPos = cutWoods[i]
                val blockAtState = level.getBlockState(cutWoodPos)
//                ForgeRegistries.BLOCKS.tags()!!.getTag(AMTags.instance!!.strippedWood).forEach {
//                    blockEntity.cutWoodIsValid = blockAtState == it
//                    return@forEach
//                }
                 for (block in blockEntity.strippedWoods) {
                     blockEntity.cutWoodIsValid = blockAtState == block
                 }
            }
            for (i in 0 until fireStones.size) {
                val fireStonePos = fireStones[i]
                val blockAtState = level.getBlockState(fireStonePos)
                blockEntity.fireStoneIsValid = blockAtState == Blocks.SMOOTH_STONE && blockEntity.fireStoneIsValid
            }
            blockEntity.structureIsValid = blockEntity.pedestalIsValid && blockEntity.bricksIsValid
                    && blockEntity.bricksWallIsValid && blockEntity.cutWoodIsValid && blockEntity.fireStoneIsValid

            val input = blockEntity.inv.getStackInSlot(0)

            if (input.isEmpty) {
                blockEntity.reset()
                return
            }

            if (blockEntity.isActive()) {
                val recipe = blockEntity.getActiveRecipe()

                if (recipe != null) {
                    blockEntity.progress++

                    val pedestalList = blockEntity.getPedestals()

                    val time = if (recipe.time != 0) recipe.time * 20 else 400

                    if (blockEntity.progress >= time) {
                        val remaining = recipe.getRemainingItems(blockEntity.recipeInv.toContainer())

                        for (i in 0 until pedestalList.size) {
                            val pedestal = pedestalList[i]
                            pedestal.getInv().setStackInSlot(0, remaining[i + 1])
                        }

                        val result = recipe.assemble(blockEntity.recipeInv.toContainer(), level.registryAccess())

                        blockEntity.setOutput(result)
                        blockEntity.reset()
                        blockEntity.changeX()
                    } else {
                        for (pedestal in pedestalList) {
                            val pedestalPos = pedestal.blockPos
                            val stack = pedestal.getInv().getStackInSlot(0)
                        }
                    }
                } else {
                    blockEntity.reset()
                }
            } else {
                blockEntity.progress = 0
            }
        }
    }

    override fun getInv(): HandleStack = this.inv

    fun createHandler(contentChanged: Runnable): HandleStack = HandleStack.create(2, contentChanged) { builder ->
        builder.setDefaultSlotLimit(1)
        builder.setCanExtract { builder.getStackInSlot(1).isEmpty }
        builder.setOutputSlots(1)
    }!!

    fun reset() {
        this.progress = 0
        this.active = false
    }

    fun isActive(): Boolean {
        if (!this.active) {
            val level = this.getLevel()
            this.active = level != null && this.structureIsValid
        }

        val activeStatus = if (this.active) "Active!" else "Inactive!"
        AMConstant.LOGGER.debug("Altar Active Status is \"${activeStatus}\"")

        return this.active
    }

    fun getActiveRecipe(): AltarRecipe? {
        if (this.getLevel() == null) return null

        val nonNullLevel = this.getLevel()!!

        this.updateInv(this.getPedestals())

        if (this.recipe == null || !this.recipe!!.matches(this.recipeInv.toContainer(), nonNullLevel)) {
            val recipe = nonNullLevel.recipeManager.getRecipeFor(AMRecipeTypes.instance!!.altarRecipe,
                this.recipeInv.toContainer(), nonNullLevel).orElse(null)
            this.recipe = if (recipe is AltarRecipe) recipe else null
        }

        return this.recipe
    }

    fun updateInv(pedestals: MutableList<AltarPedestalBlockEntity>) {
        this.recipeInv.setSize(9)
        this.recipeInv.setStackInSlot(0, this.inv.getStackInSlot(0))

        for (i in 0 until pedestals.size) {
            val stack = pedestals[i].getInv().getStackInSlot(0)
            this.recipeInv.setStackInSlot(i + 1, stack)
        }
    }

    fun getPedestals(): MutableList<AltarPedestalBlockEntity> {
        if (this.getLevel() == null) return mutableListOf()

        val pedestals: MutableList<AltarPedestalBlockEntity> = mutableListOf()

        this.getPedestalPosition().forEach { pos ->
            val blockEntity = this.getLevel()!!.getBlockEntity(pos) ?: return@forEach
            if (blockEntity is AltarPedestalBlockEntity) pedestals.add(blockEntity)
        }

        return pedestals
    }

    fun setOutput(stack: ItemStack) {
        this.inv.stacks[0] = ItemStack.EMPTY
        this.inv.stacks[1] = stack
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        this.progress = tag.getInt("AncientMagic.Altar.Progress")
        this.active = tag.getBoolean("AncientMagic.Altar.Active")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)

        tag.putInt("AncientMagic.Altar.Progress", this.progress)
        tag.putBoolean("AncientMagic.Altar.Active", this.active)
    }
}
