@file:Suppress("UNCHECKED_CAST")

package team._0mods.aeternus.api.registry.registries

import com.google.common.base.Suppliers
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.registry.registries.impl.RegistrySupplierImpl
import team._0mods.aeternus.api.util.rl
import java.util.*
import java.util.function.Supplier

class DeferredRegister<T> private constructor(
    private val regSup: Supplier<RegistrarManager>,
    private val key: ResourceKey<Registry<T>>,
    private var modId: String?
): Iterable<RegistrySupplier<T>> {
    companion object {
        @JvmStatic
        fun <T> create(key: ResourceKey<Registry<T>>, modId: String): DeferredRegister<T> {
            val value = Suppliers.memoize { RegistrarManager.get(modId) }
            return DeferredRegister(value, key, modId)
        }
    }

    private val entries: MutableList<RegistryEntry<T>> = arrayListOf()
    private val entryView = Collections.unmodifiableList(this.entries)
    private var registered = false

    val registrarManager = regSup.get()
    val registrar = regSup.get().get(key)

    fun <R: T> register(id: String, supplier: Supplier<out R>): RegistrySupplier<R> {
        if (modId == null) throw NullPointerException("You must create the deferred register with a mod id to register entries without the namespace!")
        return register("$modId:$id".rl, supplier)
    }

    fun <R: T> register(id: ResourceLocation, supplier: Supplier<out R>): RegistrySupplier<R> {
        val entry = RegistryEntry(id, supplier as Supplier<T>)
        this.entries.add(entry)
        if (registered) {
            val registrar = this.registrar
            entry.lateinitValue = registrar.register(entry.id, entry.supplier)
        }

        return entry as RegistrySupplier<R>
    }

    override fun iterator(): Iterator<RegistrySupplier<T>> = entryView.iterator()

    private inner class RegistryEntry<R>(override val id: ResourceLocation, internal val supplier: Supplier<R>): RegistrySupplierImpl<R> {
        internal lateinit var lateinitValue: RegistrySupplier<R>
        private var holderField: Holder<R>? = null

        override val holder: Holder<R>?
            get() = if (holderField != null) holderField else registrar.getHolder(id)

        override val registrarManager: RegistrarManager
            get() = this@DeferredRegister.registrarManager

        override val registrar: Registrar<R>
            get() = this@DeferredRegister.registrar as Registrar<R>

        override val registryId: ResourceLocation
            get() = key.location()

        override val isPresent: Boolean
            get() = if (this::lateinitValue.isInitialized) lateinitValue.isPresent else false

        override fun get(): R {
            if (isPresent) return lateinitValue.get()!!
            throw NullPointerException("Registry Object not present: " + this.id)
        }

        override fun hashCode(): Int = Objects.hash(registryId, id)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as RegistryEntry<*>

            if (id != other.id) return false
            if (registryId != other.registryId) return false

            return true
        }

        override fun toString(): String = "$registryId@$id"
    }
}