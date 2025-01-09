package dev.definedentity.astralgenerators.blockentities.machines

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class ProcessingMachineBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) :
    AbstractMachineBlockEntity(type, pos, state) {

    protected var cookTime: Short? = null
    protected var cookTimeTotal: Short? = null

    protected var inputItem: Item? = null
    protected var outputStack: ItemStack? = null

    override fun usesEnergy(): Boolean {
        return true
    }

    abstract override fun getMaxGeneration(): Long
    abstract override fun getEnergyPerTick(): Long
    abstract override fun getInventorySize(): Int

    override fun load(tag: CompoundTag) {
        super.load(tag)
        cookTime = tag.getShort("cookTime")
        cookTimeTotal = tag.getShort("cookTimeTotal")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putShort("cookTime", cookTime!!)
        tag.putShort("cookTimeTotal", cookTimeTotal!!)
    }

    fun getCookTime(): Short {
        return cookTime!!
    }

    fun getCookTimeTotal(): Short {
        return cookTimeTotal!!
    }

    fun finishCooking() {
        if (outputStack != null) {
            val size = outputStack!!.count + getItem(1).count
            setItem(1, ItemStack(outputStack!!.item, size))
        }
        stopCooking()
    }

    fun stopCooking() {
        this.cookTime = 0;
        this.cookTimeTotal = 0;
        this.outputStack = null;
        this.inputItem = null;
        this.setChanged();
    }

    /**
     * public <T extends CookingRecipe> CookingRecipe createRecipe(ModRecipeType<T> type, ItemStack testStack, boolean checkOutput) {
     * 		stopCooking();
     *
     * 		CookingRecipe recipe = type.findFirst(this.world, f -> f.test(testStack));
     *
     * 		if (recipe != null) {
     *
     * 			// Stop if something is already in the output.
     * 			if (checkOutput) {
     * 				ItemStack outputSlot = this.getStack(1);
     * 				ItemStack output = recipe.getOutput();
     * 				if (!outputSlot.isEmpty() && !outputSlot.getItem().equals(recipe.getOutput().getItem()) || outputSlot.getCount() + output.getCount() > outputSlot.getMaxCount()) {
     * 					return null;
     * 				}
     * 			}
     *
     * 			this.outputStack = recipe.getOutput();
     * 			this.inputItem = testStack.getItem();
     * 		}
     *
     * 		return recipe;
     * 	}
     */
}