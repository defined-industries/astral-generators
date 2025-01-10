package dev.definedentity.astralgenerators.items

import dev.definedentity.astralgenerators.AstralGenerators
import net.minecraft.ChatFormatting
import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class Wrench(properties: Item.Properties) : Item(properties) {
    companion object {
        val MODE_KEY = "Mode"
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemInHand = player.getItemInHand(usedHand)

        // Change wrench mode
        if (player.isShiftKeyDown) {
            val newMode = cycleMode(itemInHand)

            if (!level.isClientSide) {
                player.displayClientMessage(
                    TextComponent("Current mode: ").withStyle(ChatFormatting.GRAY)
                        .append(TextComponent(newMode.name).withStyle(getModeColor(newMode))),
                    true
                )
            }
            return InteractionResultHolder.success(itemInHand)
        }

        return InteractionResultHolder.pass(itemInHand)
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos
        val player = context.player
        val itemInHand = context.itemInHand

        if (player == null) return InteractionResult.PASS

        val currentMode = getCurrentMode(itemInHand)
        val blockState = level.getBlockState(clickedPos)

        when (currentMode) {
            WrenchMode.DESTROY -> {
                val blockState = level.getBlockState(clickedPos)
                val blockId = Registry.BLOCK.getKey(blockState.block)

                if (blockId.namespace == AstralGenerators.MOD_ID) {
                    if (!level.isClientSide) {
                        val droppedStack = ItemStack(blockState.block)
                        val serverLevel = level as? ServerLevel
                        if (serverLevel != null) {
                            Block.popResource(serverLevel, clickedPos, droppedStack)
                        }
                        level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3)
                    }

                    return InteractionResult.SUCCESS
                }
            }

            WrenchMode.ROTATE -> {
                if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                    if (!level.isClientSide) {
                        val currentFacing = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)
                        val newFacing = currentFacing.getClockWise(Direction.Axis.Y)
                        val newState = blockState.setValue(BlockStateProperties.HORIZONTAL_FACING, newFacing)
                        level.setBlock(clickedPos, newState, 3)
                    }

                    return InteractionResult.SUCCESS
                } else {
                    return InteractionResult.FAIL
                }
            }

            else -> {}
        }

        return InteractionResult.PASS
    }

    fun cycleMode(itemInHand: ItemStack): WrenchMode {
        val current = getCurrentMode(itemInHand)
        val next = WrenchMode.next(current)

        itemInHand.orCreateTag.putString(MODE_KEY, next.name)
        return next
    }

    private fun getCurrentMode(stack: ItemStack): WrenchMode {
        val tag = stack.tag
        return if (tag != null && tag.contains(MODE_KEY)) {
            try {
                WrenchMode.valueOf(tag.getString(MODE_KEY))
            } catch (e: IllegalArgumentException) {
                WrenchMode.DESTROY
            }
        } else {
            WrenchMode.DESTROY
        }
    }

    private fun getModeColor(mode: WrenchMode): ChatFormatting {
        return when (mode) {
            WrenchMode.DESTROY -> ChatFormatting.RED
            WrenchMode.ROTATE -> ChatFormatting.YELLOW
            WrenchMode.CONFIG -> ChatFormatting.AQUA
        }
    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced)
        val currentMode = getCurrentMode(stack)

        tooltipComponents.add(
            TextComponent("Current Mode: ").withStyle(ChatFormatting.GRAY)
                .append(TextComponent(currentMode.name).withStyle(getModeColor(currentMode)))
        )
        tooltipComponents.add(TextComponent("Hold Shift + Right Click to change modes.").withStyle(ChatFormatting.GREEN))
    }
}

enum class WrenchMode {
    DESTROY,
    ROTATE,
    CONFIG;

    companion object {
        fun next(current: WrenchMode): WrenchMode {
            val values = entries.toTypedArray()
            return values[(current.ordinal + 1) % values.size]
        }
    }
}