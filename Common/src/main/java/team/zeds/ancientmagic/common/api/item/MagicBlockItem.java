package team.zeds.ancientmagic.common.api.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.zeds.ancientmagic.common.api.cap.ItemStackMagic;
import team.zeds.ancientmagic.common.api.magic.type.MagicType;

public class MagicBlockItem extends BlockItem implements ItemStackMagic {
    private boolean canUseItem = true;
    private final MagicItemBuilder builder;

    public MagicBlockItem(Block block, MagicItemBuilder builder) {
        super(block, builder.getProperties());
        this.builder = builder;
    }

    @NotNull
    @Override
    public MagicType getMagicType() {
        return this.builder.getMagicType();
    }

    @Nullable
    @NotNull
    @Override
    public MagicType getMagicSubtype() {
        return this.builder.getMagicSubtype();
    }

    @Override
    public int getMaxMana() {
        return this.builder.getMaxMana();
    }

    @Override
    public int getManaStorages(@NotNull ItemStack stack) {
        if (stack.getOrCreateTag().get("ManaStorages") != null) {
            return stack.getOrCreateTag().getInt("ManaStorages");
        } else return 0;
    }

    @Override
    public void setManaStorages(int mana, @NotNull ItemStack stack) {
        stack.getOrCreateTag().putInt("ManaStorages", mana);
    }

    @Override
    public void addMana(int count, @NotNull ItemStack stack) {
        int storage = getManaStorages(stack);
        storage = Math.min(storage + count, this.getMaxMana());
        setManaStorages(storage, stack);
    }

    @Override
    public void subMana(int count, @NotNull ItemStack stack) {
        int storage = getManaStorages(stack);
        storage = Math.max(storage - count, 0);
        setManaStorages(storage, stack);
    }

    public InteractionResultHolder<ItemStack> useMT(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    public InteractionResult useOnMT(@NotNull UseOnContext use) {
        return InteractionResult.CONSUME;
    }

    public boolean getItemUse() {
        return this.canUseItem;
    }

    public void setItemUse(boolean val1) {
        this.canUseItem = val1;
    }

    public MagicItemBuilder getBuilder() {
        return builder;
    }

    public static MagicItemBuilder of() {
        return MagicItemBuilder.get();
    }
}
