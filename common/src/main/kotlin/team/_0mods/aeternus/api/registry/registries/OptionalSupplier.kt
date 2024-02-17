package team._0mods.aeternus.api.registry.registries

import team._0mods.aeternus.api.util.NullableSupplier
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import java.util.stream.Stream

interface OptionalSupplier<T>: NullableSupplier<T> {
    val isPresent: Boolean

    val orNull: T?
        get() {
            if (isPresent)
                return get()
            return null
        }

    fun toOptional() = Optional.ofNullable(orNull)

    fun ifPresent(action: Consumer<in T>) {
        if (isPresent)
            action.accept(get()!!)
    }

    fun ifPresentOrElse(action: Consumer<in T>, emptyAction: Runnable) {
        if (isPresent)
            action.accept(get()!!)
        else
            emptyAction.run()
    }

    fun stream(): Stream<T> {
        return if (!isPresent)
            Stream.empty()
        else Stream.of(get())
    }

    fun orElse(other: T) = if (isPresent) get() else other

    fun orElseGet(supplier: Supplier<out T>) = if (isPresent) get() else supplier.get()
}