package dev.definedentity.astralgenerators.compat.rei.assembler

import dev.definedentity.astralgenerators.blocks.machines.AGMachines
import dev.definedentity.astralgenerators.compat.rei.AstralGeneratorsREIClientPlugin
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent


class AssemblerCategory : DisplayCategory<AssemblerDisplay> {
    override fun getCategoryIdentifier(): CategoryIdentifier<AssemblerDisplay> {
        return AstralGeneratorsREIClientPlugin.ASSEMBLER
    }

    override fun getTitle(): Component {
        return TextComponent("Assembler")
    }

    override fun getIcon(): Renderer {
        return EntryStacks.of(AGMachines.ASSEMBLER.get())
    }

    override fun setupDisplay(display: AssemblerDisplay?, bounds: Rectangle?): MutableList<Widget> {
        val widgets = mutableListOf<Widget>()
        

        widgets.add(Widgets.createRecipeBase(bounds))

        if (bounds != null) {
            if (display != null) {
                for (i in 0 until 4) {
                    widgets.add(
                        Widgets.createSlot(Point(bounds.x + 10, bounds.y + 5 + i * 18))
                            .entries(display.inputEntries[i])
                            .markInput()
                    )
                }

                for (i in 0 until 4) {
                    widgets.add(
                        Widgets.createSlot(Point(bounds.x + 130, bounds.y + 5 + i * 18))
                            .entries(display.inputEntries[i + 4])
                            .markInput()
                    )
                }

                widgets.add(
                    Widgets.createSlot(Point(bounds.x + 70, bounds.y + 10))
                        .entries(display.inputEntries[8])
                        .markInput()
                )

                widgets.add(
                    Widgets.createSlot(Point(bounds.x + 70, bounds.y + 40))
                        .entries(display.outputEntries[0])
                        .markOutput()
                        .disableBackground()
                )


            }
        };
        return widgets
    }

    override fun getDisplayHeight(): Int {
        return 80
    }
}