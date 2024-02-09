@file:Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
package team._0mods.aeternus.api.event.result

import net.minecraft.world.InteractionResultHolder

class EventResultHolder<T> internal constructor(private val result: EventResult, private val t: T?) {
    companion object {
        private val PASS = EventResultHolder(EventResult.pass(), null)

        fun <T> pass(): EventResultHolder<T> = PASS as EventResultHolder<T>

        fun <T> result(result: Boolean?, obj: T): EventResultHolder<T> = EventResultHolder(EventResult.result(result), obj)

        fun <T> resultTrue(obj: T): EventResultHolder<T> = EventResultHolder(EventResult.resultTrue(), obj)

        fun <T> default(obj: T): EventResultHolder<T> = EventResultHolder(EventResult.default(), obj)

        fun <T> resultFalse(obj: T): EventResultHolder<T> = EventResultHolder(EventResult.resultFalse(), obj)
    }

    val endFurtherEvaluation: Boolean
        get() = result.endFurtherEvaluation

    val value: Boolean?
        get() = result.value

    val isEmpty: Boolean
        get() = result.isEmpty

    val isPresent: Boolean
        get() = result.isPresent

    val isTrue: Boolean
        get() = result.isTrue

    val isFalse: Boolean
        get() = result.isFalse

    fun result() = result

    val obj: T?
        get() = t

    val asInteractionResult: InteractionResultHolder<T>
        get() = InteractionResultHolder(result.asInteractionResult, t)
}