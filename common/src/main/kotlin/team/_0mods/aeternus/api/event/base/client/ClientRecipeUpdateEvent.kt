package team._0mods.aeternus.api.event.base.client

import net.minecraft.world.item.crafting.RecipeManager
import team._0mods.aeternus.api.event.core.EventFactory

fun interface ClientRecipeUpdateEvent {
    companion object {
        @JvmField val EVENT = EventFactory.createNoResult<ClientRecipeUpdateEvent>()
    }

    fun update(clientManager: RecipeManager)
}