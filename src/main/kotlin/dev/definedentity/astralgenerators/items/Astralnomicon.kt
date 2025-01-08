package dev.definedentity.astralgenerators.items

import net.minecraft.ChatFormatting
import net.minecraft.core.Registry
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import vazkii.patchouli.api.PatchouliAPI
import java.lang.IllegalArgumentException

class Astralnomicon(properties: Properties) : Item(properties) {
    companion object {
        fun isOpen(): Boolean {
            return Registry.ITEM.getKey(AGItems.ASTRALNOMICON.get()) == PatchouliAPI.get().openBookGui
        }

        fun getEdition(): Component {
            return try {
                PatchouliAPI.get().getSubtitle(Registry.ITEM.getKey(AGItems.ASTRALNOMICON.get()))
            } catch (e: IllegalArgumentException) {
                TextComponent("")
            }
        }
    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        tooltipComponents.add(getEdition().copy().withStyle(ChatFormatting.GRAY))
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(usedHand)

        if (player is ServerPlayer) {
            PatchouliAPI.get().openBookGUI(player, Registry.ITEM.getKey(this))
        }

        return InteractionResultHolder(InteractionResult.SUCCESS, stack)
    }
}