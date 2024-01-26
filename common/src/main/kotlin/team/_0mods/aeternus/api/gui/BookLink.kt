package team._0mods.aeternus.api.gui

class BookLink(
    val lineNum: Int,
    val charStartsAt: Int,
    val displayTest: String,
    val linksTo: String,
    val enabled: Boolean
) {
    var isHovered: Boolean = false
}