package team._0mods.aeternus.fabric.api.registry

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.registry.registries.AbstractRegistryProviderGetter
import team._0mods.aeternus.api.registry.registries.Registrar
import team._0mods.aeternus.api.registry.registries.RegistrarBuilder
import team._0mods.aeternus.api.registry.registries.RegistrarManager
import java.util.*
import java.util.function.Consumer

class RegistryProviderImpl: AbstractRegistryProviderGetter() {
    override fun get(modid: String): RegistrarManager.RegistryProvider = RegistrarProvider(modid)

    class RegistrarProvider(private val modId: String): RegistrarManager.RegistryProvider {
        override fun <T> get(key: ResourceKey<Registry<T>>): Registrar<T> {
            TODO("Not yet implemented")
        }

        override fun <T> get(registry: Registry<T>): Registrar<T> {
            TODO("Not yet implemented")
        }

        override fun <T> forRegistry(key: ResourceKey<Registry<T>>, consumer: Consumer<Registrar<T>>) {
            TODO("Not yet implemented")
        }

        override fun <T> builder(type: Class<T>, registryKey: ResourceLocation): RegistrarBuilder<T> {
            TODO("Not yet implemented")
        }
    }

    class RegistryEntryId<T>(private val regKey: ResourceKey<T>, private val id: ResourceLocation) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as RegistryEntryId<*>
            return Objects.equals(regKey, other.regKey) && Objects.equals(id, other.id)
        }

        override fun hashCode(): Int {
            return Objects.hash(regKey, id)
        }
    }
}