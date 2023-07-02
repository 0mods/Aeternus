package team.zeds.ancientmagic.api.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.zeds.ancientmagic.api.cap.ItemStackMagic;
import team.zeds.ancientmagic.api.magic.MagicType;

public class MagicItemOld extends Item implements ItemStackMagic {
    private boolean canUseItem = true;
    private final MagicItemBuilderOld builder;

    public MagicItemOld(MagicItemBuilderOld builder) {
        super(builder.getProperties());
        this.builder = builder;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (!stack.is(this)) return InteractionResultHolder.fail(stack);
        if (this.getItemUse()) {
            if (((this.getStorageMana(stack) != 0) && this.getStorageMana(stack) >= builder.getSubMana())
                    && this.builder.getMaxMana() != 0) {
                subMana(this.builder.getMaxMana(), stack);
                return this.useMT(level, player, hand);
            } else if ((this.getBuilder().getMaxMana() != 0 && this.getStorageMana(stack) == 0) || this.getStorageMana(stack) < this.getBuilder().getSubMana()) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true);
                return InteractionResultHolder.fail(stack);
            } else if (this.getBuilder().getMaxMana() == 0) {
                return this.useMT(level, player, hand);
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        return super.useOn(p_41427_);
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
    public int getStorageMana(@NotNull ItemStack stack) {
        if (stack.getOrCreateTag().get("StorageMana") != null) {
            return stack.getOrCreateTag().getInt("StorageMana");
        } else return 0;
    }

    @Override
    public void setStorageMana(int mana, @NotNull ItemStack stack) {
        stack.getOrCreateTag().putInt("StorageMana", mana);
    }

    @Override
    public void addMana(int count, @NotNull ItemStack stack) {
        int storage = getStorageMana(stack);
        storage = Math.min(storage + count, this.getMaxMana());
        setStorageMana(storage, stack);
    }

    @Override
    public void subMana(int count, @NotNull ItemStack stack) {
        int storage = getStorageMana(stack);
        storage = Math.max(storage - count, 0);
        setStorageMana(storage, stack);
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

    public MagicItemBuilderOld getBuilder() {
        return builder;
    }
}
