package api.ancientmagic.item;

import api.ancientmagic.magic.MagicState;
import api.ancientmagic.mod.Constant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class MagicItem extends GroupedItem implements MagicState {
    private int manaStorage = this.maxMana();

    public MagicItem(Properties p_41383_) {
        super(p_41383_);
        p_41383_.durability(this.maxMana() + 1);
    }

    public void consumeMana(int numberOfConsume) {
        this.manaStorage = this.manaStorage - numberOfConsume;
    }

    public void save(CompoundTag tag) {
        var _tag = new CompoundTag();
        _tag.put("TagOfItem", tag);
        _tag.putString("MagicTypeId", this.getMagicType().getId());
        _tag.putInt("CountOfMana", this.manaStorage);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, Player p_41433_,
                                                           @NotNull InteractionHand p_41434_) {
        var stack = p_41433_.getItemInHand(p_41434_);
        var damageValue = stack.getDamageValue();

        if (!stack.equals(new ItemStack(this))) {
            return InteractionResultHolder.fail(stack);
        }

        if (damageValue > 1) {
            this.setDamage(stack, 1);
            this.consumeMana(1);
            this.stateFunction(p_41432_, p_41433_);
            return InteractionResultHolder.success(stack);
        } else {
            this.setDamage(stack, 0);
            p_41433_.sendSystemMessage(Component.translatable("item." + Constant.Key + ".magic.nomana", this.getName(stack)));
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public List<Item> toGroup() {
        return Constant.LIST_OF_ITEMS_TO_MAGIC;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, @NotNull TooltipFlag p_41424_) {
        p_41423_.add(Component.translatable("item." + Constant.Key + ".magic.storage", this.manaStorage));
        p_41423_.add(Component.translatable("item." + Constant.Key + "magic.type", this.getMagicType().getId()));
    }
}
