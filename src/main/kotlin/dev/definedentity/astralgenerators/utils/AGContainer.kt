package dev.definedentity.astralgenerators.utils

import net.minecraft.core.NonNullList
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.Containers
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

interface AGContainer : Container {
    companion object {
        /**
         * Creates an inventory from the item list.
         */
        fun of(items: NonNullList<ItemStack>): AGContainer {
            return object : AGContainer {
                override fun getItems(): NonNullList<ItemStack> = items
            }
        }

        /**
         * Creates a new inventory with the specified size.
         */
        fun ofSize(slots: Int): AGContainer {
            return of(NonNullList.withSize(slots, ItemStack.EMPTY))
        }
    }

    /**
     * Retrieves the item list of this inventory.
     * Must return the same instance every time it's called.
     */
    fun getItems(): NonNullList<ItemStack>

    /**
     * Returns the inventory size.
     */
    override fun getContainerSize(): Int {
        return getItems().size
    }

    /**
     * Checks if the inventory is empty.
     * @return true if this inventory has only empty stacks, false otherwise.
     */
    override fun isEmpty(): Boolean {
        for (itemStack in getItems()) {
            if (!itemStack.isEmpty) {
                return false
            }
        }
        return true
    }

    /**
     * Retrieves the item in the slot.
     */
    override fun getItem(slot: Int): ItemStack {
        return getItems()[slot]
    }

    /**
     * Removes all items from an inventory slot.
     * @param slot The slot to remove from.
     */
    override fun removeItemNoUpdate(slot: Int): ItemStack {
        return ContainerHelper.takeItem(getItems(), slot)
    }

    /**
     * Removes items from an inventory slot.
     * @param slot  The slot to remove from.
     * @param amount How many items to remove. If there are less items in the slot than what are requested,
     *              takes all items in that slot.
     */
    override fun removeItem(slot: Int, amount: Int): ItemStack {
        val result = ContainerHelper.removeItem(getItems(), slot, amount)

        if (!result.isEmpty) {
            setChanged()
        }
        return result
    }

    /**
     * Replaces the current stack in an inventory slot with the provided stack.
     * @param slot  The inventory slot of which to replace the itemstack.
     * @param stack The replacing itemstack. If the stack is too big for
     *              this inventory ({@link Inventory#getMaxCountPerStack()}),
     *              it gets resized to this inventory's maximum amount.
     */
    override fun setItem(slot: Int, stack: ItemStack) {
        getItems()[slot] = stack

        if (stack.count > maxStackSize) {
            stack.count = maxStackSize
        }
    }

    /**
     * Clears the inventory.
     */
    override fun clearContent() {
        getItems().clear()
    }

    /**
     * Marks the state as dirty.
     * Must be called after changes in the inventory, so that the game can properly save
     * the inventory contents and notify neighboring blocks of inventory changes.
     */
    override fun setChanged() {
    }

    /**
     * @return true if the player can use the inventory, false otherwise.
     */
    override fun stillValid(player: Player): Boolean {
        return true
    }
}
