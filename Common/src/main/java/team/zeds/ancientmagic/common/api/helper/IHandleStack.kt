package team.zeds.ancientmagic.common.api.helper

import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import java.util.function.BiFunction
import java.util.function.Function

interface IHandleStack {
    fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean, container: Boolean): ItemStack
    fun extractItem(slot: Int, amount: Int, container: Boolean): ItemStack
    fun getStacks(): NonNullList<ItemStack>
    fun getOutputSlots(): IntArray
    fun setDefaultSlotLimit(size: Int)
    fun addSlotLimit(slot: Int, size: Int)
    fun setCanInsert(validator: BiFunction<Int, ItemStack, Boolean>)
    fun setCanExtract(canExtract: Function<Int, Boolean>)
    fun setOutputSlots(vararg slots: Int)
    fun toContainer(): Container
    fun <T: IHandleStack> copy(): T
    // WARNING! AUTOGEN. DON'T TOUCH IT! ONLY FIRST USE
    fun deserializeTag(tag: CompoundTag)
    fun serializeTag(): CompoundTag
    fun getStackInSlotHandler(slot: Int): ItemStack
    fun setStackInSlot(slot: Int, stack: ItemStack)
    fun <T: IHandleStack> nsCreate(size: Int): T
    fun setSizeHandler(int: Int)
}