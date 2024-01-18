package team._0mods.aeternus.mixin.entity

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.animal.Chicken
import net.minecraft.world.level.Level
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(Chicken::class)
abstract class ChickenMixin(type: EntityType<out Animal>, level: Level) : Animal(type, level) {
    @Inject(method = ["registerGoals"], at = [At("TAIL")])
    fun registerGoalsInj(ci: CallbackInfo) {
        super.registerGoals()
    }
}