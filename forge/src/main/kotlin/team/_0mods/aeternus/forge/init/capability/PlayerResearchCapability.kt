package team._0mods.aeternus.forge.init.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.common.util.LazyOptional
import team._0mods.aeternus.api.magic.research.player.PlayerResearch
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.forge.api.emptyLazyOpt
import team._0mods.aeternus.forge.api.lazyOptOf
import team._0mods.aeternus.common.init.AeternusCorePlugin
import team._0mods.aeternus.api.util.rl

class PlayerResearchCapability: PlayerResearch {
    private val researchList: MutableList<Research> = mutableListOf()

    override val researches: List<Research>
        get() = this.researchList.toList() // Copy from list

    override fun addResearch(vararg research: Research) {
        researchList.addAll(research)
    }

    fun save(): ListTag {
        val tag = ListTag()

        this.researches.forEach {
            tag.add(StringTag.valueOf(it.name.toString()))
        }

        return tag
    }

    fun load(tag: ListTag?) {
        if (tag != null) {
            for (i in 0 ..< tag.size) {
                val founded = tag.getString(i)

                if (!researches.stream().noneMatch { it.name == founded.rl })
                    continue
                else {
                    val foundedResearch = AeternusCorePlugin.researchRegistry.getResearchById(founded.rl) ?: continue
                    this.addResearch(foundedResearch)
                }
            }
        }
    }

    class Provider: ICapabilityProvider, INBTSerializable<ListTag> {
        private var cap: PlayerResearchCapability? = null
        private val lazy = lazyOptOf(this::createCap)

        private fun createCap(): PlayerResearchCapability {
            if (this.cap == null)
                this.cap = PlayerResearchCapability()

            return this.cap!!
        }

        override fun <T : Any?> getCapability(p0: Capability<T>, p1: Direction?): LazyOptional<T> {
            if (p0 == AFCapabilities.playerResearch)
                return lazy.cast()

            return emptyLazyOpt()
        }

        override fun serializeNBT(): ListTag = createCap().save()

        override fun deserializeNBT(p0: ListTag?) = createCap().load(p0)
    }
}