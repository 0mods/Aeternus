package team._0mods.multilib.fabric.mixin

import net.minecraft.world.entity.Entity
import net.minecraft.world.level.entity.EntityInLevelCallback
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.ModifyVariable
import team._0mods.multilib.fabric.hooks.FabricEntityHooks

@Mixin(Entity::class)
class EntityMixin {
    @ModifyVariable(method = ["setLevelCallback"], argsOnly = true, ordinal = 0, at = At("HEAD"))
    fun modifyLevelCallbackSetLevelCallback(callback: EntityInLevelCallback?): EntityInLevelCallback? {
        val obj = this as Any
        return FabricEntityHooks.wrapEntityInLevelCallback(obj as Entity, callback)
    }
}
