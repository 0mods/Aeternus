package team.zeds.ancientmagic.api.registry

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import team.zeds.ancientmagic.AMConstant.reloc
import team.zeds.ancientmagic.api.except.UnsupportedRegistryException

interface IAMRegistryEntry {

    companion object {
        @JvmStatic val noTabItems: MutableList<Item> = mutableListOf()

        @JvmStatic val registeredItems: MutableList<Item> = mutableListOf()
        @JvmStatic val registeredBlocks: MutableList<Block> = mutableListOf()
    }
}