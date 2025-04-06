package dev.toby0207.astralgenerators.utils

import java.util.Locale

object TextFormatting {
    fun toEnglishName(name: Any): String {
        return name.toString().lowercase(Locale.ROOT).split("_").joinToString(" ") {
            it.replaceFirstChar { char -> char.uppercase() }
        }
    }
}