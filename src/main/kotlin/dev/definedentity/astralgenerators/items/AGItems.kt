package dev.definedentity.astralgenerators.items

import dev.definedentity.astralgenerators.AstralGenerators
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.items.upgrades.EnergyUpgrade
import dev.definedentity.astralgenerators.items.upgrades.SpeedUpgrade
import net.minecraft.world.item.Item

object AGItems {

    val ASTRALNOMICON = REGISTRATE.item("astralnomicon", ::Astralnomicon).register()

    val WRENCH = REGISTRATE.item("wrench", ::Wrench).register()

    val SPEED_UPGRADE = REGISTRATE.item("speed_upgrade", ::SpeedUpgrade).properties { p -> p.stacksTo(1) }.register()
    val ENERGY_UPGRADE = REGISTRATE.item("energy_upgrade", ::EnergyUpgrade).properties { p -> p.stacksTo(1) }.register()

    fun init() {
    }
}