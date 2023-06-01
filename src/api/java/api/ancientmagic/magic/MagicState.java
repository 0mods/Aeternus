package api.ancientmagic.magic;

import api.ancientmagic.block.MagicBlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface MagicState {
    //Everything
    MagicType getMagicType();

    MagicType getMagicSubtype();

    void onActive(Level level, Player player, InteractionHand hand);

    //Item
    int getMaxMana(ItemStack stack, MagicBlockEntity entity);

    void consumeMana(int numberOfConsume, ItemStack stack, MagicBlockEntity entity);

    int getStorageMana(ItemStack stack, MagicBlockEntity entity);

    void addMana(int countOfAddition, ItemStack stack, MagicBlockEntity entity);
}
