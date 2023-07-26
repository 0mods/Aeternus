package team.zeds.ancientmagic.init.registries

import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import team.zeds.ancientmagic.api.mod.AMConstant
import team.zeds.ancientmagic.api.mod.AMConstant.reloc

class AMTags private constructor() {
    companion object {
        @get:JvmStatic
        var instance: AMTags? = null
            get() {
                if (field == null) instance = AMTags()
                return field!!
            }
            private set //no value setter
    }

    private val registeredTags = mutableListOf<TagKey<*>>()

    val unconsumeCatalyst: TagKey<Item> = item("unbreakable_tp")
    val consumeCatalyst: TagKey<Item> = item("consume_tp")
    val strippedWood = block("stripped_wood")

    fun init() {
        AMConstant.LOGGER.debug("Tag {} are registered", registeredTags)
    }

    private fun item(id: String): TagKey<Item> {
        val registered = ItemTags.create(reloc(id))
        registeredTags.add(registered)
        return registered
    }

    private fun block(id: String): TagKey<Block> {
        val registered = BlockTags.create(reloc(id))
        registeredTags.add(registered)
        return registered
    }
}
