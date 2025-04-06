package dev.toby0207.astralgenerators.data.items

import dev.toby0207.astralgenerators.AstralGenerators
import dev.toby0207.astralgenerators.data.AGContent

object AGItems {
    val ASTRALNOMICON = AGContent.ITEMS.registerItem(Astralnomicon.ID) { Astralnomicon() }
    val WRENCH = AGContent.ITEMS.registerItem(Wrench.ID) { Wrench() }

    fun init() {}
}