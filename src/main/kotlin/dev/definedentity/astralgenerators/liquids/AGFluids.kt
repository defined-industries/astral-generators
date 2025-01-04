package dev.definedentity.astralgenerators.liquids

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.AGIdentifier

object AGFluids {
    val STEAM = REGISTRATE.fluid(
        "steam", AGIdentifier("fluid/steam"), AGIdentifier("fluid/steam_flow")
    ).lang("Steam")
        .properties { p -> p.blastResistance(100f) }
        .noBucket()
        .register()

    val ENERGY = REGISTRATE.fluid(
        "energy", AGIdentifier("fluid/energy"), AGIdentifier("fluid/energy")
    ).lang("Energy")
        .properties { p -> p.blastResistance(100f) }
        .noBucket()
        .register()

    fun init() {}
}