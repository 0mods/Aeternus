/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.util

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.resources.ResourceLocation
import java.util.function.Supplier
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class DelegatedRegistry<V>(val asReg: RegistrySupplier<V>): ReadOnlyProperty<Any?, V>, Supplier<V>, () -> V {
    override fun getValue(thisRef: Any?, property: KProperty<*>): V = asReg.toOptional().orElseThrow { NullPointerException("Failed to get Registry value") }

    override fun get(): V = asReg.toOptional().orElseThrow { NullPointerException("Failed to get Registry value") }

    override fun invoke(): V = asReg.toOptional().orElseThrow { NullPointerException("Failed to get Registry value") }
}

fun <V, T: V> DeferredRegister<V>.reg(name: String, obj: () -> T): DelegatedRegistry<T> {
    val reg = this.register(name, obj)
    return DelegatedRegistry(reg)
}

fun <V, T: V> DeferredRegister<V>.reg(id: ResourceLocation, obj: () -> T): DelegatedRegistry<T> {
    val reg = this.register(id, obj)
    return DelegatedRegistry(reg)
}