package dev.definedentity.astralgenerators.utils

import net.minecraft.core.NonNullList
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.Containers
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

interface AGContainer : Container {
    companion object {
        fun of(items: NonNullList<ItemStack>): AGContainer {
            return object : AGContainer {
                override fun getItems(): NonNullList<ItemStack> = items
            }
        }

        fun create(slots: Int): AGContainer {
            return object : AGContainer {
                private val inventory: NonNullList<ItemStack> = NonNullList.withSize(slots, ItemStack.EMPTY)

                override fun getItems(): NonNullList<ItemStack> = inventory
            }
        }
    }

    fun getItems(): NonNullList<ItemStack>

    override fun getContainerSize(): Int {
        return getItems().size
    }

    override fun isEmpty(): Boolean {
        for (itemStack in getItems()) {
            if (!itemStack.isEmpty) {
                return false
            }
        }
        return true
    }

    override fun getItem(slot: Int): ItemStack {
        return getItems()[slot]
    }

    override fun removeItemNoUpdate(slot: Int): ItemStack {
        return ContainerHelper.takeItem(getItems(), slot)
    }

    override fun removeItem(slot: Int, amount: Int): ItemStack {
        val result = ContainerHelper.removeItem(getItems(), slot, amount)

        if (!result.isEmpty) {
            setChanged()
        }
        return result
    }

    override fun setItem(slot: Int, stack: ItemStack) {
        getItems()[slot] = stack

        if (stack.count > maxStackSize) {
            stack.count = maxStackSize
        }
    }

    override fun clearContent() {
        getItems().clear()
    }

    override fun setChanged() {
    }

    override fun stillValid(player: Player): Boolean {
        return true
    }
}