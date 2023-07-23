package team.zeds.ancientmagic.api.handler

import net.minecraft.core.NonNullList
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.ItemStackHandler
import org.apache.commons.lang3.ArrayUtils
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

open class HandleStack protected constructor(size: Int, @JvmField val contentChange: Runnable): ItemStackHandler(size) {
    @JvmField val slotSizeMap: MutableMap<Int, Int>
    @JvmField var canInsert: BiFunction<Int, ItemStack, Boolean>? = null
    @JvmField var canExtract: Function<Int, Boolean>? = null
    @JvmField var maxStackSize = 64
    @JvmField var outputSlots: IntArray? = null

    init {
        slotSizeMap = HashMap()
    }

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack =
        this.insert(slot, stack, simulate, false)


    open fun insert(slot: Int, stack: ItemStack, simulate: Boolean, container: Boolean): ItemStack {
        if (!container && this.outputSlots != null && ArrayUtils.contains(this.outputSlots, slot)) return stack
        return super.insertItem(slot, stack, simulate)
    }

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack =
        extract(slot, amount, simulate, false)

    open fun extract(slot: Int, amount: Int, simulate: Boolean, container: Boolean): ItemStack {
        if (!container) {
            if (this.canExtract != null && !this.canExtract!!.apply(slot)) return ItemStack.EMPTY
            if (this.outputSlots != null && !ArrayUtils.contains(this.outputSlots, slot)) return ItemStack.EMPTY
        }
        return super.extractItem(slot, amount, simulate)
    }

    override fun getSlotLimit(slot: Int): Int = if (this.slotSizeMap.containsKey(slot)) this.slotSizeMap[slot]!! else this.maxStackSize

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        return canInsert == null || canInsert!!.apply(slot, stack)
    }

    override fun onContentsChanged(slot: Int) {
        this.contentChange.run()
    }

    open fun getStacks(): NonNullList<ItemStack?>? {
        return stacks
    }

    open fun getOutputSlots(): IntArray? {
        return outputSlots
    }

    open fun setDefaultSlotLimit(size: Int) {
        maxStackSize = size
    }

    open fun addSlotLimit(slot: Int, size: Int) {
        slotSizeMap.put(slot, size)
    }

    open fun setCanInsert(validator: BiFunction<Int, ItemStack, Boolean>) {
        canInsert = validator
    }

    open fun setCanExtract(canExtract: Function<Int, Boolean>) {
        this.canExtract = canExtract
    }

    open fun setOutputSlots(vararg slots: Int) {
        outputSlots = slots
    }

    open fun toContainer(): Container {
        return SimpleContainer(*stacks.toTypedArray<ItemStack>())
    }

    open fun copy(): HandleStack {
        val newInventory = HandleStack(this.slots, this.contentChange)

        newInventory.setDefaultSlotLimit(maxStackSize)
        newInventory.setCanInsert(canInsert!!)
        newInventory.setCanExtract(canExtract!!)
        newInventory.setOutputSlots(*outputSlots!!)

        slotSizeMap.forEach(newInventory::addSlotLimit)

        for (i in 0 until this.slots) {
            val stack = getStackInSlot(i)
            newInventory.setStackInSlot(i, stack.copy())
        }

        return newInventory
    }

    companion object {
        @JvmStatic fun create(size: Int): HandleStack = create(size) { _ -> }
        @JvmStatic fun create(size: Int, contentChange: Runnable): HandleStack = create(size, contentChange) { _ -> }
        @JvmStatic fun create(size: Int, builder: Consumer<HandleStack>): HandleStack = create(size, null, builder)

        @JvmStatic
        fun create(size: Int, contentChange: Runnable?, builder: Consumer<HandleStack>): HandleStack {
            val handler = HandleStack(size, contentChange!!)
            builder.accept(handler)
            return handler
        }
    }
}