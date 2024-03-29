/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNCHECKED_CAST")

package team._0mods.multilib.registries

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.multilib.service.ServiceProvider
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

@ApiStatus.NonExtendable
@ApiStatus.Internal
interface AbstractRegistryProvider {
    fun get(modid: String): RegistrarManager.RegistrarProvider
}

class RegistrarManager private constructor(modId: String) {
    companion object {
        private val MANAGER = ConcurrentHashMap<String, RegistrarManager>()

        @JvmStatic
        fun get(modid: String) = MANAGER.computeIfAbsent(modid, ::RegistrarManager)

        @JvmStatic
        fun <T> getId(obj: T, fallback: ResourceKey<Registry<T>>?): ResourceLocation? {
            if (fallback == null)
                return null
            return getId(obj, BuiltInRegistries.REGISTRY.get(fallback.location()) as Registry<T>)
        }

        @JvmStatic
        fun <T> getId(obj: T, fallback: Registry<T>?): ResourceLocation? {
            if (fallback == null)
                return null
            return fallback.getKey(obj)
        }
    }

    private val provider = ServiceProvider.registry.registryProvider.get(modId)

    fun <T> get(registry: ResourceKey<Registry<T>>) = provider.get(registry)

    fun <T> get(registry: Registry<T>) = provider.get(registry)

    fun <T> forRegistry(key: ResourceKey<Registry<T>>, callback: Consumer<Registrar<T>>) = provider.forRegistry(key, callback)

    @SafeVarargs
    fun <T> builder(registryId: ResourceLocation, vararg typeGetter: T): RegistrarBuilder<T> {
        if (typeGetter.isNotEmpty()) throw IllegalStateException("Array must be empty! Founded values: $typeGetter")
        return provider.builder(typeGetter.javaClass.componentType as Class<T>, registryId)
    }

    @ApiStatus.Internal
    interface RegistrarProvider {
        fun <T> get(key: ResourceKey<Registry<T>>): Registrar<T>

        fun <T> get(registry: Registry<T>): Registrar<T>

        fun <T> forRegistry(key: ResourceKey<Registry<T>>, consumer: Consumer<Registrar<T>>)

        fun <T> builder(type: Class<T>, registryKey: ResourceLocation): RegistrarBuilder<T>
    }
}
