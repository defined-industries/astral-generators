package dev.definedentity.astralgenerators.compat.rei.assembler

import dev.definedentity.astralgenerators.blocks.machines.AGMachines
import dev.definedentity.astralgenerators.compat.rei.AstralGeneratorsREIClientPlugin
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent

class AssemblerCategory : DisplayCategory<AssemblerDisplay> {
    override fun getCategoryIdentifier(): CategoryIdentifier<out AssemblerDisplay?>? {
        return AstralGeneratorsREIClientPlugin.ASSEMBLER
    }

    override fun getTitle(): Component {
        return TextComponent("Assembler")
    }

    override fun getIcon(): Renderer {
        return EntryStacks.of(AGMachines.ASSEMBLER.get())
    }
}