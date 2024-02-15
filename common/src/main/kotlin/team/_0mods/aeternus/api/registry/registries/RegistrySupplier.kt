package team._0mods.aeternus.api.registry.registries

import net.minecraft.core.Holder
import org.jetbrains.annotations.ApiStatus
import java.util.function.Consumer

@ApiStatus.NonExtendable
interface RegistrySupplier<T>: DeferredSupplier<T>, Holder<T> {
    val registrarManager: RegistrarManager

    val registrar: Registrar<T>

    fun listen(callback: Consumer<T>) {
        registrar.listen(this, callback)
    }
}