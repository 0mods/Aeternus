package team._0mods.multilib.fabric.mixin

import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.EntityEvent
import team._0mods.multilib.extension.ItemExtension

@Mixin(LivingEntity::class)
class LivingEntityMixin {
    @Inject(method = ["hurt"], at = [At("HEAD")], cancellable = true)
    private fun hurt(damageSource: DamageSource, f: Float, cir: CallbackInfoReturnable<Boolean>) {
        val obj = this as Any
        if (obj is Player) return
        if (EntityEvent.HURT.event.hurt(obj as LivingEntity, damageSource, f).isFalse) {
            cir.setReturnValue(false)
        }
    }

    @Inject(method = ["getEquipmentSlotForItem"], at = [At("HEAD")], cancellable = true)
    private fun getEquipmentSlotForItem(stack: ItemStack, cir: CallbackInfoReturnable<EquipmentSlot>) {
        val item = stack.item
        if (item is ItemExtension) {
            val slot = item.getCustomEquipSlot(stack)
            if (slot != null) {
                cir.setReturnValue(slot)
            }
        }
    }
}
