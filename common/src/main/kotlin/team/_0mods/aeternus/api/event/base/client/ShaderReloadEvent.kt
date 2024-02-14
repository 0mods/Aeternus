package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.server.packs.resources.ResourceProvider
import team._0mods.aeternus.api.event.core.EventFactory
import java.util.function.Consumer

interface ShaderReloadEvent {
    companion object {
        @JvmField val EVENTg = EventFactory.createNoResult<ShaderReloadEvent>()
    }

    fun reload(provider: ResourceProvider, actor: RegistryActor)

    @FunctionalInterface
    interface RegistryActor {
        fun registerShaders(shader: ShaderInstance, callback: Consumer<ShaderInstance>)
    }
}