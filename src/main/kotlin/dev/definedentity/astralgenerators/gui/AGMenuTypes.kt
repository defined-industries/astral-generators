package dev.definedentity.astralgenerators.gui

import dev.definedentity.astralgenerators.gui.machines.AssemblerMenu
import dev.definedentity.astralgenerators.utils.AGIdentifier
import net.minecraft.core.Registry
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

object AGMenuTypes {
    val ASSEMBLER_MENU =
        Registry.register(
            Registry.MENU,
            AGIdentifier("assembler_menu"),
            MenuType { id, inv -> AssemblerMenu(id, inv, ContainerLevelAccess.NULL) }
        )

    fun init() {}
}
