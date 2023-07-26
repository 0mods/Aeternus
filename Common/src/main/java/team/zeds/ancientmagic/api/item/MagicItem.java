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

public class MagicItem extends Item implements ItemStackMagic {
    private boolean canUseItem = true;
    private final MagicItemBuilder builder;

    public MagicItem(MagicItemBuilder builder) {
        super(builder.getProperties());
        this.builder = builder;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (!stack.is(this)) return InteractionResultHolder.fail(stack);
        if (this.getItemUse()) {
            if (this.getMaxMana() != 0 && ((this.getManaStorages(stack) != 0) && this.getManaStorages(stack) >= this.getBuilder().getSubMana())) {
                subMana(this.builder.getSubMana(), stack);
                return this.useMT(level, player, hand);
            } else if ((this.getMaxMana() > 0 && this.getManaStorages(stack) == 0) && this.getManaStorages(stack) < this.getBuilder().getSubMana()) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true);
                return InteractionResultHolder.fail(stack);
            } else if (this.getMaxMana() == 0) {
                return this.useMT(level, player, hand);
            }
        } else return InteractionResultHolder.fail(stack);

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var stack = ctx.getItemInHand();
        if (!stack.is(this)) return InteractionResult.FAIL;
        if (this.getItemUse()) {
            if (this.getMaxMana() != 0 && ((this.getManaStorages(stack) != 0) && this.getManaStorages(stack) >= this.getBuilder().getSubMana())) {
                subMana(this.builder.getSubMana(), stack);
                return this.useOnMT(ctx);
            } else if ((this.getMaxMana() > 0 && this.getManaStorages(stack) == 0) && this.getManaStorages(stack) < this.getBuilder().getSubMana()) {
                ctx.getPlayer().displayClientMessage(MagicType.getMagicMessage("notMana", getName(stack)), true);
                return InteractionResult.FAIL;
            } else if (this.getMaxMana() == 0) {
                return this.useOnMT(ctx);
            }
        } else return InteractionResult.FAIL;

        return InteractionResult.CONSUME;
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
