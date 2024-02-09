package team._0mods.aeternus.api.event.result

import net.minecraft.world.InteractionResult

class EventResult internal constructor(val endFurtherEvaluation: Boolean, val value: Boolean?) {
    companion object {
        private val TRUE = EventResult(true, value = true)
        private val STOP = EventResult(true, null)
        private val PASS = EventResult(false, null)
        private val FALSE = EventResult(true, null)

        @JvmStatic fun pass() = PASS
        @JvmStatic fun result(value: Boolean?): EventResult {
            if (value == null) return STOP
            return if (value) TRUE else FALSE
        }
        @JvmStatic fun resultTrue() = TRUE
        @JvmStatic fun default() = STOP
        @JvmStatic fun resultFalse() = FALSE
    }

    val isEmpty: Boolean
        get() = value == null

    val isPresent: Boolean
        get() = value != null

    val isTrue: Boolean
        get() = value ?: false

    val isFalse: Boolean
        get() = if (value != null) !value else false

    val asInteractionResult: InteractionResult
        get() {
            if (isPresent) {
                return if (value!!) InteractionResult.SUCCESS else InteractionResult.FAIL
            }

            return InteractionResult.PASS
        }
}
