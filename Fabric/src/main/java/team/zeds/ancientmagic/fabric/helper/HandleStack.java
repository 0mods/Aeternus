package team.zeds.ancientmagic.fabric.helper;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.zeds.ancientmagic.common.api.helper.IHandleStack;
import team.zeds.ancientmagic.fabric.forged.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class HandleStack extends ItemStackHandler implements IHandleStack {
    private final Runnable onContentsChanged;
    private final Map<Integer, Integer> slotSizeMap;
    private BiFunction<Integer, ItemStack, Boolean> canInsert = null;
    private Function<Integer, Boolean> canExtract = null;
    private int maxStackSize = 64;
    protected int[] outputSlots = null;
    private final int[] availableSlots;

    protected HandleStack(int size, Runnable onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
        this.slotSizeMap = new HashMap<>();

        availableSlots = new int[getContainerSize()];
        for (int i = 0; i < getContainerSize(); i++) {
            availableSlots[i] = i;
        }
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack) {
        return this.insertItem(slot, stack, false, false);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate, boolean container) {
        return !container && this.outputSlots != null && ArrayUtils.contains(this.outputSlots, slot) ? stack : super.insertItem(slot, stack);
    }

    public ItemStack extractItem(int slot, int amount) {
        return this.extractItem(slot, amount, false);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean container) {
        if (!container) {
            if (this.canExtract != null && !(Boolean)this.canExtract.apply(slot)) {
                return ItemStack.EMPTY;
            }

            if (this.outputSlots != null && !ArrayUtils.contains(this.outputSlots, slot)) {
                return ItemStack.EMPTY;
            }
        }

        return super.removeItem(slot, amount);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.slotSizeMap.containsKey(slot) ? this.slotSizeMap.get(slot) : this.maxStackSize;
    }

    @Override
    public boolean canPlaceItem(int i, ItemStack itemStack) {
        return this.canInsert == null || this.canInsert.apply(i, itemStack);
    }

    @Override
    public void clearContent() {
        this.stacks.clear();
        this.setChanged();
    }

    @Override
    public void setChanged() {
        if (this.onContentsChanged != null)
            this.onContentsChanged.run();
    }

    @NotNull
    @Override
    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

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
    public void setCanInsert(BiFunction<Integer, ItemStack, Boolean> validator) {
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

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public HandleStack copy() {
        final var newInv = new HandleStack(this.getContainerSize(), this.onContentsChanged);

        newInv.setDefaultSlotLimit(this.maxStackSize);
        newInv.setCanInsert(this.canInsert);
        newInv.setOutputSlots(this.outputSlots);

        this.slotSizeMap.forEach(newInv::addSlotLimit);

        for (int i = 0; i < this.getContainerSize(); i++) {
            final var stack = this.getItem(i);

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
        return this.getItem(slot);
    }

    @SuppressWarnings("unchecked")
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
        return create(size, (builder) -> {
        });
    }

    public static HandleStack create(int size, Runnable onContentsChanged) {
        return create(size, onContentsChanged, (builder) -> {
        });
    }

    public static HandleStack create(int size, Consumer<HandleStack> builder) {
        return create(size, null, builder);
    }

    public static HandleStack create(int size, Runnable onContentsChanged, Consumer<HandleStack> builder) {
        final var handler = new HandleStack(size, onContentsChanged);
        builder.accept(handler);
        return handler;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return canPlaceItem(i, itemStack);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return availableSlots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return (canExtract == null || canExtract.apply(i)) && (outputSlots == null || ArrayUtils.contains(outputSlots, i));
    }
}
