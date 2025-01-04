package dev.definedentity.astralgenerators

import com.tterrag.registrate.Registrate
import dev.definedentity.astralgenerators.blocks.AGBlocks
import dev.definedentity.astralgenerators.liquids.AGFluids
import dev.definedentity.astralgenerators.utils.AGIdentifier
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.world.item.Items

object AstralGenerators : ModInitializer {
    val MOD_ID: String = "astralgenerators"
    val MOD_NAME: String = "Astral Generators"

    val REGISTRATE = Registrate.create(MOD_ID)

    val ITEM_GROUP = FabricItemGroupBuilder.build(AGIdentifier("general")) {
        Items.DIRT.asItem().defaultInstance
    }

    override fun onInitialize() {
        initializeItemGroups()

        AGBlocks.init()
        AGFluids.init()

        REGISTRATE.register()
    }

    private fun initializeItemGroups() {
        REGISTRATE.creativeModeTab { ITEM_GROUP }

        REGISTRATE.addRawLang("itemGroup.$MOD_ID.general", MOD_NAME)
    }
}
