package team._0mods.multilib.fabric.mixin

import net.minecraft.core.NonNullList
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.extension.ItemExtension

@Mixin(Inventory::class)
class InventoryMixin {
    @Shadow
    @Final
    var armor: NonNullList<ItemStack>? = null

    @Shadow
    @Final
    var player: Player? = null

    @Inject(method = ["tick"], at = [At("RETURN")])
    private fun updateItems(ci: CallbackInfo) {
        for (stack in armor!!) {
            val item = stack.item
            if (item is ItemExtension) {
                item.tickArmor(stack, player!!)
            }
        }
    }
}
