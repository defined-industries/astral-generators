package dev.definedentity.astralgenerators.gui.machines

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class AssemblerScreen(menu: AssemblerMenu, inv: Inventory, title: Component) :
    CottonInventoryScreen<AssemblerMenu>(menu, inv.player, title) {
        
    }
