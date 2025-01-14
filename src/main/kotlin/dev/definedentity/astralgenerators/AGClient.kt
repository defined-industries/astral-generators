package dev.definedentity.astralgenerators

import dev.definedentity.astralgenerators.gui.AGMenuTypes
import dev.definedentity.astralgenerators.gui.machines.AssemblerScreen
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.gui.screens.MenuScreens

object AGClient : ClientModInitializer {
    override fun onInitializeClient() {

        MenuScreens.register(AGMenuTypes.ASSEMBLER_MENU) { type, playerInventory, title ->
            AssemblerScreen(type, playerInventory, title)
        }

    }
}