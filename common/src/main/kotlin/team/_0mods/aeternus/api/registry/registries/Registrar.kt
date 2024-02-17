/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:Suppress("UNCHECKED_CAST")

package team._0mods.aeternus.api.registry.registries

import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier

interface Registrar<T>: Iterable<T> {
    fun delegate(id: ResourceLocation): RegistrySupplier<T>

    fun <R: T> wrap(obj: R): RegistrySupplier<R> {
        val id = getId(obj)

        return if (id == null)
            throw IllegalArgumentException("Cannot wrap an object without an id: $obj")
        else delegate(id) as RegistrySupplier<R>
    }

    fun <E: T> register(id: ResourceLocation, supplier: Supplier<E>): RegistrySupplier<E>

    fun getId(obj: T): ResourceLocation?

    fun getRawId(obj: T): Int

    fun getKey(obj: T): Optional<ResourceKey<T>>

    fun get(id: ResourceLocation): T?

    fun byRawId(id: Int): T?

    fun contains(id: ResourceLocation): Boolean

    fun containsValue(obj: T): Boolean

    fun getIds(): Set<ResourceLocation>

    fun entrySet(): Set<Map.Entry<ResourceKey<T>, T>>

    fun key(): ResourceKey<out Registry<T>>

    fun getHolder(key: ResourceKey<T>): Holder<T>?

    fun getHolder(id: ResourceLocation) = getHolder(ResourceKey.create(key(), id))

    fun <R: T> listen(supplier: RegistrySupplier<R>, callback: Consumer<R>) {
        listen(supplier.id) { callback.accept(it as R) }
    }

    fun listen(id: ResourceLocation, consumer: Consumer<T>)
}
