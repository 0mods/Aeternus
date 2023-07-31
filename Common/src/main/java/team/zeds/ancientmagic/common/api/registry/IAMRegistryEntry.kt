package team.zeds.ancientmagic.common.api.registry

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import team.zeds.ancientmagic.common.api.recipe.AMChancedRecipeSerializer
import team.zeds.ancientmagic.common.api.recipe.AMRecipeSerializer
import team.zeds.ancientmagic.common.block.AltarBlock
import team.zeds.ancientmagic.common.block.AltarPedestalBlock
import team.zeds.ancientmagic.common.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.common.block.entity.AltarPedestalBlockEntity
import team.zeds.ancientmagic.common.recipes.AltarRecipe
import team.zeds.ancientmagic.common.recipes.ManaGenerationRecipe

interface IAMRegistryEntry {
    fun getAltarBlock(): AltarBlock
    fun getAltarBlockEntityType(): BlockEntityType<AltarBlockEntity>
    fun getAltarPedestalBlockEntityType(): BlockEntityType<AltarPedestalBlockEntity>
    fun getAltarPedestalBlock(): AltarPedestalBlock
    fun getAltarRecipeSerializer(): AMRecipeSerializer<AltarRecipe>
    fun getManaRecipeSerializer(): AMChancedRecipeSerializer<ManaGenerationRecipe>

    companion object {
        @JvmStatic val registeredItems: MutableList<Item> = mutableListOf()
    }
}