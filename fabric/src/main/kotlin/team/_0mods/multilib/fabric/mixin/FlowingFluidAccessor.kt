package team._0mods.multilib.fabric.mixin

import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.material.FlowingFluid
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(LiquidBlock::class)
interface FlowingFluidAccessor {
    @Accessor("fluid")
    fun getFluid(): FlowingFluid
}