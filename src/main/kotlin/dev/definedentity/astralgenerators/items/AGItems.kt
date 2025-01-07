package dev.definedentity.astralgenerators.items

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import net.minecraft.world.item.Item

object AGItems {

    val ASTRALNOMICON = REGISTRATE.item("astralnomicon", ::Item).register()

    fun init() {}
}