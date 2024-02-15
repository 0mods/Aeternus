package team._0mods.aeternus.common.impl.event

import net.minecraft.world.item.ItemStack
import team._0mods.aeternus.api.event.base.client.TooltipEvent

class TooltipAdditionalContext: TooltipEvent.AdditionalContents {
    companion object {
        private val threadLocal = ThreadLocal.withInitial(::TooltipAdditionalContext)

        @JvmStatic
        fun get(): TooltipAdditionalContext = threadLocal.get()
    }

    private var stack: ItemStack? = null

    override var item: ItemStack?
        get() = stack
        set(value) {
            this.stack = value
        }
}