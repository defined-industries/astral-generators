package dev.definedentity.astralgenerators.blocks

import dev.definedentity.astralgenerators.blocks.ess.ESSBlocks
import dev.definedentity.astralgenerators.materials.AGMaterials

object AGBlocks {
    fun init() {
        AGMaterials.init()
        AGGlasses.init()
        AGCasings.init()

        // Multiblocks
        ESSBlocks.init()
    }
}