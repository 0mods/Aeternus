package team.zeds.aeternus.api.magic

interface IPlayerEtherium {

    fun getEtherium(etherium: Int)

    operator fun plus(count: Int)

    operator fun minus(count: Int)
}