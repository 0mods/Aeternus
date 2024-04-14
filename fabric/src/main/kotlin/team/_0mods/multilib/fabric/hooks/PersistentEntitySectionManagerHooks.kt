package team._0mods.multilib.fabric.hooks

import net.minecraft.server.level.ServerLevel

interface PersistentEntitySectionManagerHooks {
    fun mlAttachLevel(level: ServerLevel)
}