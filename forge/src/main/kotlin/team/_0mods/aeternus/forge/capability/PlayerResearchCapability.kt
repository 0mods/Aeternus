package team._0mods.aeternus.forge.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.common.util.LazyOptional
import team._0mods.aeternus.api.magic.research.IPlayerResearch
import team._0mods.aeternus.api.magic.research.IResearch
import team._0mods.aeternus.forge.api.emptyLazyOpt
import team._0mods.aeternus.forge.api.lazyOptOf

class PlayerResearchCapability: IPlayerResearch {
    private var researchesArray: MutableMap<IResearch, Boolean> = mutableMapOf()

    override fun getResearches(): MutableMap<IResearch, Boolean> = this.researchesArray

    override fun set(research: IResearch, have: Boolean) {
        researchesArray[research] = have
    }

    override fun plus(research: IResearch) {
        researchesArray[research] = true
    }

    fun save(): ListTag {
        val tag = ListTag()
        val compound = CompoundTag()

        this.researchesArray.forEach {
            compound.putBoolean(it.key.getName().toString(), it.value)
        }

        tag.add(compound)

        return tag
    }

    fun load(tag: ListTag?) {
        if (tag != null) {
            for (i in 0 ..< tag.size) {
                IResearch.registriedResearch.forEach {
                    val compound = tag.getCompound(i)

                    if (compound.contains(it.getName().toString())) {
                        this.researchesArray[it] = compound.getBoolean(it.getName().toString())
                    }
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

        override fun deserializeNBT(p0: ListTag?) {
            createCap().load(p0)
        }
    }
}