/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package team._0mods.aeternus.mixin

import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.aeternus.api.event.EntityHurtEvent
import team._0mods.aeternus.api.util.c

@Mixin(Entity::class)
class HurtEventInvoker {
    @Inject(method = ["hurt"], at = [At("HEAD")], cancellable = true)
    fun checkRecipes(source: DamageSource, amount: Float, cir: CallbackInfoReturnable<Boolean?>) {
        if (EntityHurtEvent.EVENT.invoker().hurt(this.c(), source, amount).isFalse) {
            cir.returnValue = false
        }
    }
}
