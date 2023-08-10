package team.zeds.ancientmagic.forge.api;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import team.zeds.ancientmagic.common.api.helper.IHandleStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class HandleStack extends ItemStackHandler implements IHandleStack<HandleStack> {
    private final Runnable onContentsChanged;
    private final Map<Integer, Integer> slotSizeMap;
    private BiFunction<Integer, ItemStack, Boolean> canInsert = null;
    private Function<Integer, Boolean> canExtract = null;
    private int maxStackSize = 64;
    private int[] outputSlots = null;

    protected HandleStack(int size, Runnable onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
        this.slotSizeMap = new HashMap<>();
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return this.insertItem(slot, stack, simulate, false);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate, boolean container) {
        if (!container && this.outputSlots != null && ArrayUtils.contains(this.outputSlots, slot))
            return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.extractItem(slot, amount, simulate, false);
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate, boolean container) {
        if (!container) {
            if (this.canExtract != null && !this.canExtract.apply(slot))
                return ItemStack.EMPTY;

            if (this.outputSlots != null && !ArrayUtils.contains(this.outputSlots, slot))
                return ItemStack.EMPTY;
        }

        return super.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.slotSizeMap.containsKey(slot) ? this.slotSizeMap.get(slot) : this.maxStackSize;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return this.canInsert == null || this.canInsert.apply(slot, stack);
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (this.onContentsChanged != null)
            this.onContentsChanged.run();
    }

    @NotNull
    @Override
    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    @NotNull
    @Override
    public int[] getOutputSlots() {
        return this.outputSlots;
    }

    @Override
    public void setDefaultSlotLimit(int size) {
        this.maxStackSize = size;
    }

    @Override
    public void addSlotLimit(int slot, int size) {
        this.slotSizeMap.put(slot, size);
    }

    @Override
    public void setCanInsert(@NotNull BiFunction<Integer, ItemStack, Boolean> validator) {
        this.canInsert = validator;
    }

    @Override
    public void setCanExtract(@NotNull Function<Integer, Boolean> canExtract) {
        this.canExtract = canExtract;
    }

    @Override
    public void setOutputSlots(@NotNull int... slots) {
        this.outputSlots = slots;
    }

    @NotNull
    @Override
    public Container toContainer() {
        return new SimpleContainer(this.stacks.toArray(new ItemStack[0]));
    }

    @NotNull
    @Override
    public HandleStack copy() {
        final var newInv = new HandleStack(this.getSlots(), this.onContentsChanged);

        newInv.setDefaultSlotLimit(this.maxStackSize);
        newInv.setCanInsert(this.canInsert);
        newInv.setCanExtract(this.canExtract);
        newInv.setOutputSlots(this.outputSlots);

        this.slotSizeMap.forEach(newInv::addSlotLimit);

        for (int i = 0; i < this.getSlots(); i++) {
            var stack = this.getStackInSlot(i);

            newInv.setStackInSlot(i, stack.copy());
        }

        return newInv;
    }

    @Override
    public void deserializeTag(@NotNull CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    @NotNull
    @Override
    public CompoundTag serializeTag() {
        return this.serializeNBT();
    }

    @NotNull
    @Override
    public ItemStack getStackInSlotHandler(int slot) {
        return this.getStackInSlot(slot);
    }

    @NotNull
    @Override
    public HandleStack nsCreate(int size) {
        return HandleStack.create(size);
    }

    @Override
    public void setSizeHandler(int i) {
        this.setSize(i);
    }

    public static HandleStack create(int size) {
        return create(size, builder -> {});
    }

    public static HandleStack create(int size, Runnable contentChange) {
        return create(size, contentChange, builder -> {});
    }

    public static HandleStack create(int size, Consumer<HandleStack> builder) {
        return create(size, null, builder);
    }

    public static HandleStack create(int size, Runnable contentChange, Consumer<HandleStack> builder) {
        var handler = new HandleStack(size, contentChange);
        builder.accept(handler);
        return handler;
    }
}
