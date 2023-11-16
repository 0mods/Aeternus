package team.zeds.aeternus.api.magic

interface IPlayerEtherium {
    val etherium: Int

    fun addEtherium(count: Int)

    fun consumeEtherium(count: Int)
}