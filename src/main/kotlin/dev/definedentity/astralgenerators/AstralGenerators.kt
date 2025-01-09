package dev.definedentity.astralgenerators

import com.tterrag.registrate.Registrate
import dev.definedentity.astralgenerators.blockentities.AGBlockEntities
import dev.definedentity.astralgenerators.blocks.AGBlocks
import dev.definedentity.astralgenerators.blocks.casings.AGCasings
import dev.definedentity.astralgenerators.items.AGItems
import dev.definedentity.astralgenerators.material_sets.AGMaterialSets
import dev.definedentity.astralgenerators.utils.AGIdentifier
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.world.item.Items
import org.slf4j.LoggerFactory

object AstralGenerators : ModInitializer {
    val MOD_ID: String = "astralgenerators"
    val MOD_NAME: String = "Astral Generators"

    val LOGGER = LoggerFactory.getLogger(MOD_ID)

    val REGISTRATE = Registrate.create(MOD_ID)

    val ITEM_GROUP = FabricItemGroupBuilder.build(AGIdentifier("general")) {
        AGItems.ASTRALNOMICON.get().defaultInstance
    }

    override fun onInitialize() {
        initializeItemGroups()

        AGBlockEntities.init()
        AGMaterialSets.init()
        AGBlocks.init()
        AGItems.init()

        REGISTRATE.register()
    }

    private fun initializeItemGroups() {
        REGISTRATE.creativeModeTab { ITEM_GROUP }

        REGISTRATE.addRawLang("itemGroup.$MOD_ID.general", MOD_NAME)
    }
}
