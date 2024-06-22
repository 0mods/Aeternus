package team._0mods.aeternus.api.util

import team._0mods.aeternus.platformredirect.api.util.textLiteral
import team._0mods.aeternus.platformredirect.api.util.textTranslate

// It implements by Component
interface Text {
    companion object
}

fun Text.Companion.translate(id: String, vararg any: Any) = if (any.isNotEmpty()) id.textTranslate(any) else id.textLiteral

fun Text.Companion.literal(text: String) = text.textLiteral
