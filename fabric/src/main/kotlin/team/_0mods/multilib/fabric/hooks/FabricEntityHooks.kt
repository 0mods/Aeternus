package team._0mods.multilib.fabric.hooks

import net.minecraft.core.SectionPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.entity.EntityInLevelCallback
import team._0mods.multilib.event.base.common.EntityEvent

object FabricEntityHooks {
    fun wrapEntityInLevelCallback(entity: Entity, callback: EntityInLevelCallback?): EntityInLevelCallback? {
        if (callback === EntityInLevelCallback.NULL) return callback
        if (callback == null) return callback
        return object : EntityInLevelCallback {
            private var lastSectionKey = SectionPos.asLong(entity.blockPosition())

            override fun onMove() {
                callback.onMove()
                val currentSectionKey = SectionPos.asLong(entity.blockPosition())
                if (currentSectionKey != lastSectionKey) {
                    EntityEvent.ENTER_SECTION.event.enterSection(
                        entity, SectionPos.x(lastSectionKey), SectionPos.y(lastSectionKey),
                        SectionPos.z(lastSectionKey), SectionPos.x(currentSectionKey), SectionPos.y(currentSectionKey),
                        SectionPos.z(currentSectionKey)
                    )
                    lastSectionKey = currentSectionKey
                }
            }

            override fun onRemove(removalReason: Entity.RemovalReason) {
                callback.onRemove(removalReason)
            }
        }
    }
}