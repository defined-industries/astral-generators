package dev.definedentity.astralgenerators.gui.machines

import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.gui.AGMenuTypes
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.WText
import io.github.cottonmc.cotton.gui.widget.data.Insets
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
        root.setSize(120, 150)
        root.insets = Insets.ROOT_PANEL
        root.add(createPlayerInventoryPanel(), 0, 6)

        // Input slots
        val inputSlots = WItemSlot.of(blockInventory, 0, 3, 3)
        root.add(inputSlots, 2, 1)

        // Output slot
        val outputSlot = WItemSlot.outputOf(blockInventory, 9)
        root.add(outputSlot, 7, 2)

        // DEBUG
        root.add(WText(TextComponent(propertyDelegate.get(0).toString())), 1, 1)
        root.add(WText(TextComponent(propertyDelegate.get(1).toString())), 1, 2)

        root.validate(this)
    }
}
