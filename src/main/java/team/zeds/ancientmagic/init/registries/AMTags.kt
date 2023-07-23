package team.zeds.ancientmagic.init.registries

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import team.zeds.ancientmagic.api.mod.AMConstant

object AMTags {
    @JvmField var UNCONSUMABLE_TELEPORTATION_CATALYST: TagKey<Item>? = null
    @JvmField var CONSUMABLE_TELEPORTATION_CATALYST: TagKey<Item>? = null

    @JvmStatic
    fun init() {
        UNCONSUMABLE_TELEPORTATION_CATALYST = createTag("unbreakable_tp")
        CONSUMABLE_TELEPORTATION_CATALYST = createTag("consume_tp")
    }

    @JvmStatic
    private fun createTag(tagName: String): TagKey<Item> {
        val tag: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation(AMConstant.KEY, tagName))

        AMConstant.LOGGER.debug("{} has been registered or initialized!", tag.location)
        return tag
    }
}
