package team._0mods.multilib.fabric.hooks

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import team._0mods.multilib.event.base.client.ScreenInputEvent

interface ScreenInputDelegate {
    fun ml_delInputs(): Screen

    class DelegateScreen(private var parent: Screen) : Screen(Component.empty()) {
        override fun charTyped(codePoint: Char, modifiers: Int): Boolean {
            if (ScreenInputEvent.CHAR_TYPED_PRE.event.onPress(Minecraft.getInstance(), parent, codePoint, modifiers).isPresent)
                return true
            if (parent.charTyped(codePoint, modifiers))
                return true
            return ScreenInputEvent.CHAR_TYPED_POST.event.onPress(Minecraft.getInstance(), parent, codePoint, modifiers).isPresent
        }
    }
}