package team._0mods.aeternus.api.util

import com.google.common.base.Supplier
import java.util.function.Consumer
import java.util.function.IntConsumer
import java.util.function.IntSupplier

fun interface FloatSupplier {
    fun getAsFloat(): Float
}

interface Value<T>: Supplier<T>, Consumer<T>

interface BaseNumberValue<T: Number>: Value<T> {
    var asT: T
}

interface IntValue: BaseNumberValue<Int>, IntSupplier, IntConsumer {
    override fun getAsInt(): Int = this.asT

    override fun accept(p0: Int) {
        this.asT = p0
    }
}
