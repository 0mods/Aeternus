package api.ancientmagic.item;

import api.ancientmagic.magic.IMagicType;
import api.ancientmagic.magic.MagicState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class MagicItem extends Item implements MagicState {
    private final int maxStorageMana;
    private final ItemStack stack;

    public MagicItem(Properties p_41383_, int maxStorageMana) {
        super(p_41383_);
        p_41383_.durability(this.maxMana() + 1);
        this.maxStorageMana = maxStorageMana;
        this.stack = new ItemStack(this);
    }

    @Override
    public void consumeMana(int numberOfConsume) {
        var startMana = this.getStorageMana();
        var consumedMana = startMana - numberOfConsume;
        this.stack.getOrCreateTag().putInt("ManaStorage", consumedMana);
    }

    @Override
    public void addMana(int countOfAddition) {
        var storageMana = this.getStorageMana();
        var additionMana = storageMana + countOfAddition;
        if (storageMana > this.maxMana()) {
            this.stack.getOrCreateTag().putInt("ManaStorage", additionMana);
        }
    }

    @Override
    public int getStorageMana() {
        return this.stack.getOrCreateTag().getInt("ManaStorage");
    }

    @Override
    public int maxMana() {
        this.stack.getOrCreateTag().putInt("MaxManaStorage", this.maxStorageMana);
        this.stack.getOrCreateTag().putInt("ManaStorage", 0);
        return maxStorageMana;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player,
                                                           @NotNull InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var damageValue = stack.getDamageValue();

        if (!stack.is(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (damageValue > 1 && this.maxMana() != 0) {
            this.setDamage(stack, 1);
            this.consumeMana(1);
            this.magicState(level, player, hand);
            return InteractionResultHolder.success(stack);
        } else if (damageValue > 1 && this.maxMana() != 0) {
            this.setDamage(stack, 0);
            player.sendSystemMessage(IMagicType.magicTooltip("notMana", this.getName(stack)));
            return InteractionResultHolder.fail(stack);
        } else if (this.maxMana() == 0) {
            this.magicState(level, player, hand);
        }
        return InteractionResultHolder.pass(stack);
    }
}
