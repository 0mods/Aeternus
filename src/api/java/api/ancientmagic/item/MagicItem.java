package api.ancientmagic.item;

import api.ancientmagic.group.AncientMagicTabs;
import api.ancientmagic.magic.MagicState;
import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class MagicItem extends Item implements MagicState {
    private int manaStorage = this.maxMana();

    public MagicItem(Properties p_41383_) {
        super(p_41383_);
        p_41383_.durability(this.maxMana() + 1);
    }

    public void consumeMana(int numberOfConsume) {
        this.manaStorage = manaStorage - numberOfConsume;
    }

    @Override
    public int getStoragedMana() {
        return this.manaStorage;
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
            player.sendSystemMessage(Component.translatable(String.format("item.%s.magic.nomana", Constant.Key), this.getName(stack)));
            return InteractionResultHolder.fail(stack);
        } else if (this.maxMana() == 0) {
            this.magicState(level, player, hand);
        }
        return InteractionResultHolder.pass(stack);
    }
}
