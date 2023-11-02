package team.zeds.ancientmagic.common.api.cap

interface BlockEtherium {
    fun getEtheriumStorage(): Int

    fun setEtheriumStorage(storage: Int)

    fun getMaxEtheriumStorage(): Int

    fun setMaxEtheriumStorage(storage: Int)

    fun addEtherium(count: Int)

    fun subEtherium(count: Int)
}