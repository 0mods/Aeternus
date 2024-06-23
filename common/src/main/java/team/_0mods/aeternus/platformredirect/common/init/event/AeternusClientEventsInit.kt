package team._0mods.aeternus.platformredirect.common.init.event

import net.minecraft.client.Minecraft
import ru.hollowhorizon.hc.common.events.SubscribeEvent
import ru.hollowhorizon.hc.common.events.registry.RegisterKeyBindingsEvent
import ru.hollowhorizon.hc.common.events.tick.TickEvent
import team._0mods.aeternus.platformredirect.client.keys.AeternusKeys
import team._0mods.aeternus.platformredirect.client.keys.registries
import team._0mods.aeternus.platformredirect.client.screen.configScreen

object AeternusClientEventsInit {
    @JvmStatic
    @SubscribeEvent(clientSideOnly = true)
    fun onButtonClick(e: TickEvent.Client) {
        while (AeternusKeys.configGUIOpen.consumeClick()) {
            Minecraft.getInstance().setScreen(configScreen())
        }
    }

    @JvmStatic
    @SubscribeEvent(clientSideOnly = true)
    fun onKeyRegister(e: RegisterKeyBindingsEvent) {
        registries.forEach(e::registerKeyMapping)
    }
}
