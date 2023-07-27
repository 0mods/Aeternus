package team.zeds.ancientmagic.api.helper

import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack

interface IHandleStack {
    fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean, container: Boolean): ItemStack
    fun extractItem(slot: Int, stack: ItemStack, simulate: Boolean, container: Boolean): ItemStack
    fun getStacks(): NonNullList<ItemStack>
    fun getOutputSlots(): Array<Int>
    fun setDefaultSlotLimit(size: Int)
    fun addSlotLimit(slot: Int, size: Int)
    fun setCanInsert(validator: (Int, ItemStack) -> Boolean)
    fun setCanExtract(canExtract: (Int) -> Boolean)
    fun setOutputSlots(slots: Array<Int>)
    fun toContainer(): Container
    fun copy(): IHandleStack
    // WARNING! AUTOGEN. DON'T TOUCH IT! ONLY FIRST USE
    fun deserializeTag(tag: CompoundTag)
    fun serializeTag(): CompoundTag
    fun getStackInSlotHandler(slot: Int): ItemStack
    fun setStackInSlot(slot: Int, stack: ItemStack)
    fun <T: IHandleStack> create(size: Int): T
    fun setSizeHandler(int: Int)
}