/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.mixin.goal

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.animal.Panda
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

@Mixin(Panda.PandaAttackGoal::class)
abstract class PandaAttackGoalMixin(mob: Panda, speedMod: Double, followIfNotSeen: Boolean) : MeleeAttackGoal(mob, speedMod,
    followIfNotSeen
) {
    @Shadow
    @Final
    private lateinit var panda: Panda

    @Inject(method = ["canUse"], at = [At("RETURN")], cancellable = true)
    fun canUseInj(cir: CallbackInfoReturnable<Boolean>) {
        if (panda.level().isNight && panda.level().dimensionTypeId() == AeternusRegsitry.iterDimType)
            cir.returnValue = true
        else cir.returnValue = panda.canPerformAction() && super.canUse()
    }
}
