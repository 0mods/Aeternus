package team._0mods.aeternus.api.magic

interface IPlayerEtherium {
    fun getEtheriumCount(): Int

    operator fun plus(count: Int)

    operator fun minus(count: Int)
}