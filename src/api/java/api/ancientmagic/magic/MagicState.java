package api.ancientmagic.magic;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface MagicState {
    IMagicType getMagicType();

    int maxMana(ItemStack stack);

    void consumeMana(int numberOfConsume, ItemStack stack);

    int getStorageMana(ItemStack stack);

    void addMana(int countOfAddition, ItemStack stack);

    void onActive(Level level, Player player, InteractionHand hand);
}
