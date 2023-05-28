package api.ancientmagic.item;

import api.ancientmagic.group.AncientMagicTabs;
import api.ancientmagic.magic.MagicState;
import api.ancientmagic.mod.Constant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class MagicItem extends HasGroupItem implements MagicState {
    private int manaStorage = this.maxMana();

    public MagicItem(Properties p_41383_) {
        super(p_41383_, AncientMagicTabs.MAGIC_ITEMS);
        p_41383_.durability(this.maxMana() + 1);

    }

    public void consumeMana(int numberOfConsume) {
        CompoundTag tag = new CompoundTag();

        this.manaStorage = manaStorage - numberOfConsume;

        this.save(tag);
    }

    public void save(CompoundTag tag) {
        tag.put("TagOfItem", tag);
        tag.putString("MagicTypeName", this.getMagicType().getName());
        tag.putInt("CountOfMana", this.manaStorage);
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
        CompoundTag tag = new CompoundTag();

        if (!stack.is(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (damageValue > 1 && this.maxMana() != 0) {
            this.setDamage(stack, 1);
            this.consumeMana(1);
            this.stateFunction(level, player, hand);
            this.save(tag);
            return InteractionResultHolder.success(stack);
        } else if (damageValue > 1 && this.maxMana() != 0) {
            this.setDamage(stack, 0);
            player.sendSystemMessage(Component.translatable(String.format("item.%s.magic.nomana", Constant.Key), this.getName(stack)));
            return InteractionResultHolder.fail(stack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
