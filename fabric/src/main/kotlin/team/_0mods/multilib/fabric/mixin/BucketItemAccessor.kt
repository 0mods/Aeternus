package team._0mods.multilib.fabric.mixin

import net.minecraft.world.item.BucketItem
import net.minecraft.world.level.material.Fluid
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(BucketItem::class)
interface BucketItemAccessor {
    @Accessor("content")
    fun getContent(): Fluid
}