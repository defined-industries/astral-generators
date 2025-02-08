package dev.definedentity.astralgenerators.compat.rei.assembler

import dev.definedentity.astralgenerators.AstralGenerators
import dev.definedentity.astralgenerators.blocks.machines.AGMachines
import dev.definedentity.astralgenerators.compat.rei.AstralGeneratorsREIClientPlugin
import dev.definedentity.astralgenerators.compat.rei.widgets.AssemblerArrorWidget
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack


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
            val startX = bounds.x + 10
            val startY = bounds.y + 10
            val slotSize = 18

            if (display != null) {
                for (index in 0 until 9) {
                    val row = index / 3
                    val col = index % 3
                    val x = startX + col * slotSize
                    val y = startY + row * slotSize

                    val inputEntry = display.inputEntries.getOrNull(index) ?: EntryIngredient.of(EntryStacks.of(
                        ItemStack.EMPTY))
                    widgets.add(
                        Widgets.createSlot(Point(x, y))
                            .entries(inputEntry)
                            .markInput()
                    )
                }

                val arrowX = startX + 3 * slotSize + 5
                val arrowY = startY + slotSize

                widgets.add(
                    AssemblerArrorWidget(
                        arrowX, arrowY,
                        ResourceLocation(AstralGenerators.MOD_ID, "textures/gui/widget_progress_empty.png"),
                        ResourceLocation(AstralGenerators.MOD_ID, "textures/gui/widget_progress_full.png"),
                        18, 18,
                        progressProvider = {
                            val cycleTime = 2000L
                            val time = System.currentTimeMillis() % cycleTime
                            time.toFloat() / cycleTime.toFloat()
                        }
                    )
                )

                val outputX = startX + 3 * slotSize + 25
                val outputY = startY + slotSize
                widgets.add(
                    Widgets.createSlot(Point(outputX, outputY))
                        .entries(display.outputEntries[0])
                        .markOutput()
                        .disableBackground()
                )


            }
        };
        return widgets
    }

    override fun getDisplayHeight(): Int {
        return 70
    }

    override fun getDisplayWidth(display: AssemblerDisplay?): Int {
        return 130
    }
}