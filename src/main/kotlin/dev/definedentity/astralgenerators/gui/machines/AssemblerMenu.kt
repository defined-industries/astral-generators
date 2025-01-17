package dev.definedentity.astralgenerators.gui.machines

import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.gui.AGMenuTypes
import dev.definedentity.astralgenerators.utils.AGIdentifier
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.WDynamicLabel
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.WTabPanel
import io.github.cottonmc.cotton.gui.widget.WText
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType

class AssemblerMenu(syncId: Int, playerInventory: Inventory, ctx: ContainerLevelAccess) :
    SyncedGuiDescription(
        AGMenuTypes.ASSEMBLER_MENU,
        syncId,
        playerInventory,
        getBlockInventory(ctx, AssemblerEntity.CONTAINER_SIZE),
        getBlockPropertyDelegate(ctx, AssemblerEntity.CONTAINER_DATA_SIZE)
    ) {

    init {
        val root = WGridPanel()
        setRootPanel(root)

        // Base
        root.setSize(162, 162)
        root.insets = Insets(9)
        root.add(createPlayerInventoryPanel(), 0, 6)

        // Input slots
        val inputSlots = WItemSlot(blockInventory, 0, 3, 3, false)
        root.add(inputSlots, 2, 1)

        // Output slot
        val outputSlot = WItemSlot(blockInventory, 9, 1, 1, false)
        outputSlot.isInsertingAllowed = false
        root.add(outputSlot, 8, 2)

        // Energy bar
        val energyBar = object : WBar(
            AGIdentifier("textures/gui/widget_energy_empty.png"),
            AGIdentifier("textures/gui/widget_energy_full.png"),
            0,
            1
        ) {
            override fun addTooltip(information: TooltipBuilder) {
                val energy = propertyDelegate.get(0)
                val capacity = propertyDelegate.get(1)
                information.add(TextComponent("Energy: $energy/$capacity").withStyle(ChatFormatting.GRAY))
            }
        }
        root.add(
            energyBar, 0, 1, 1, 3
        )

        // Progress bar
        val progressBar = WBar(
            AGIdentifier("textures/gui/widget_progress_empty.png"),
            AGIdentifier("textures/gui/widget_progress_full.png"),
            2,
            3
        )
        root.add(
            progressBar, 6, 2, 1, 1
        )

        root.validate(this)
    }
}
