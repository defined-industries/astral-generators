package dev.definedentity.astralgenerators.blocks

import dev.definedentity.astralgenerators.materials.AGMaterials

object AGBlocks {
    fun init() {
        AGMaterials.init()
        AGBatteries.init()
        AGGlasses.init()
        AGCasings.init()
        AGPorts.init()
    }
}