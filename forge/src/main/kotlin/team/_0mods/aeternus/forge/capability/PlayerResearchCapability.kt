package team._0mods.aeternus.forge.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
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

    fun save(): CompoundTag {
        val tag = CompoundTag()

        this.getResearches().entries.forEach {
            val research = it.key
            val isOpened = it.value

            tag.putBoolean(research.getName(), isOpened)
        }

        return tag
    }

    fun load(tag: CompoundTag?) {
        if (tag != null) {
            IResearch.registriedResearch.forEach { research ->
                researchesArray[research] = tag.getBoolean(research.getName())
            }
        }
    }

    class Provider: ICapabilityProvider, INBTSerializable<CompoundTag> {
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

        override fun serializeNBT(): CompoundTag = createCap().save()

        override fun deserializeNBT(p0: CompoundTag?) {
            createCap().load(p0)
        }
    }
}