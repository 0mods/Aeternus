package team.zeds.ancientmagic.common.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import team.zeds.ancientmagic.common.AMConstant
import team.zeds.ancientmagic.common.api.block.ContainerBlockEntity
import team.zeds.ancientmagic.common.api.helper.IHandleStack
import team.zeds.ancientmagic.common.api.helper.StructurePosition
import team.zeds.ancientmagic.common.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.common.platform.AMServices
import team.zeds.ancientmagic.common.recipes.AltarRecipe

class AltarBlockEntity(blockPos: BlockPos, state: BlockState) : ContainerBlockEntity(
    AMServices.PLATFORM.getIAMRegistryEntry().getAltarBlockEntityType(),
    blockPos, state
) {
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
    private val inv: IHandleStack
    private val recipeInv: IHandleStack

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
        inv = createHandler(this::changeX)
        recipeInv = AMServices.PLATFORM.getIHandleStackForAltarBlockEntityRecipeInventory(9)
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
            val input = blockEntity.inv.getStackInSlotHandler(0)

            if (input.isEmpty) {
                blockEntity.reset()
                return
            }

            if (blockEntity.getStructureValid()) {
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
                            val stack = pedestal.getInv().getStackInSlotHandler(0)
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

    override fun getInv(): IHandleStack = this.inv

    override fun createHandler(contextChange: Runnable): IHandleStack =
        AMServices.PLATFORM.getIHandleStackForAltarBlockEntity(contextChange)

    fun reset() {
        this.progress = 0
    }

    fun getActiveRecipe(): AltarRecipe? {
        if (this.getLevel() == null) return null

        val nonNullLevel = this.getLevel()!!

        this.updateInv(this.getPedestals())

        if (this.recipe == null || !this.recipe!!.matches(this.recipeInv.toContainer(), nonNullLevel)) {
            val recipe = nonNullLevel.recipeManager.getRecipeFor(
                AMRecipeTypes.instance!!.altarRecipe,
                this.recipeInv.toContainer(), nonNullLevel).orElse(null)
            this.recipe = if (recipe is AltarRecipe) recipe else null
        }

        return this.recipe
    }

    fun updateInv(pedestals: MutableList<AltarPedestalBlockEntity>) {
        this.recipeInv.setSizeHandler(9)
        this.recipeInv.setStackInSlot(0, this.inv.getStackInSlotHandler(0))

        for (i in 0 until pedestals.size) {
            val stack = pedestals[i].getInv().getStackInSlotHandler(0)
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

    fun getStructureValid(): Boolean {
        val pedestals = this.getPedestalPosition()
        val bricks = this.getBricksPosition()
        val bricksWall = this.getBricksWallPosition()
        val cutWoods = this.getCutWoodPosition()
        val fireStones = this.fireStonePosition()

        val strippedWoods = this.strippedWoods
        val level = this.getLevel()

        if (level != null) {
            for (bPos in pedestals) {
                val blockAtState = level.getBlockState(bPos).block
                this.pedestalIsValid = blockAtState == AMServices.PLATFORM.getIAMRegistryEntry().getAltarPedestalBlock()
            }

            for (bPos in bricks) {
                val blockAtState = level.getBlockState(bPos).block
                this.bricksIsValid = blockAtState == Blocks.STONE_BRICKS
            }

            for (bPos in bricksWall) {
                val blockAtState = level.getBlockState(bPos).block
                this.bricksWallIsValid = blockAtState == Blocks.STONE_BRICK_WALL
            }

            for (bPos in fireStones) {
                val blockAtState = level.getBlockState(bPos).block
                this.fireStoneIsValid = blockAtState == Blocks.SMOOTH_STONE
            }

            for (bPos in cutWoods) {
                val blockAtState = level.getBlockState(bPos).block
                for (block in strippedWoods) {
                    this.cutWoodIsValid = blockAtState == block
                }
            }

            return this.pedestalIsValid && this.bricksIsValid && this.bricksWallIsValid && this.fireStoneIsValid && this.cutWoodIsValid
        } else return false
    }

    fun setOutput(stack: ItemStack) {
        this.inv.getStacks()[0] = ItemStack.EMPTY
        this.inv.getStacks()[1] = stack
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