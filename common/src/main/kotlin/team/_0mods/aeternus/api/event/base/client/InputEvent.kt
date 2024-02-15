package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.Minecraft
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult

interface InputEvent {
    companion object {
        @JvmField val MOUSE_SCROLL = EventFactory.createEventResult<MouseScroll>()

        @JvmField val MOUSE_CLICK_PRE = EventFactory.createEventResult<MouseClick>()
        @JvmField val MOUSE_CLICK_POST = EventFactory.createEventResult<MouseClick>()

        @JvmField val KEY_PRESS = EventFactory.createEventResult<Key>()
    }

    fun interface Key {
        fun onPress(minecraft: Minecraft, key: Int, scanCode: Int, action: Int, modifiers: Int): EventResult
    }

    fun interface MouseScroll {
        fun onScroll(minecraft: Minecraft, amount: Double): EventResult
    }

    fun interface MouseClick {
        fun onClicked(minecraft: Minecraft, button: Int, action: Int, modifiers: Int): EventResult
    }
}
