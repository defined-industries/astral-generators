package dev.definedentity.astralgenerators.compat.rei.widgets

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import me.shedaniel.rei.api.client.gui.widgets.Widget
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.resources.ResourceLocation

class AssemblerArrorWidget (
    private val x: Int,
    private val y: Int,
    private val textureStart: ResourceLocation,
    private val textureEnd: ResourceLocation,
    private val width: Int,
    private val height: Int,

    private val progressProvider: () -> Float
) : Widget() {

    override fun render(matrices: PoseStack, mouseX: Int, mouseY: Int, delta: Float) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderTexture(0, textureStart)
        GuiComponent.blit(
            matrices, x, y,
            0f, 0f,
            width, height,
            width, height
        )


        val progress = progressProvider().coerceIn(0f, 1f)
        val fillWidth = (progress * width).toInt().coerceAtMost(width)

        RenderSystem.setShaderTexture(0, textureEnd)

        GuiComponent.blit(
            matrices, x, y,
            0f,0f,
            fillWidth, height,
            width, height
        )
    }
    //* Shutting up some error :)
    override fun children(): List<GuiEventListener> {
        return emptyList()
    }
}