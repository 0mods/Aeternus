package api.ancientmagic.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicItemReturnableUse {
    private final Player player;
    private final Level level;
    private final InteractionHand hand;
    private InteractionResultHolder<ItemStack> resultHolder;

    public MagicItemReturnableUse(Player player, Level level, InteractionHand hand) {
        this.player = player;
        this.level = level;
        this.hand = hand;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

    public InteractionResultHolder<ItemStack> getReturnValue() {
        return this.resultHolder;
    }

    public void setReturnValue(InteractionResultHolder<ItemStack> resultHolder) {
        this.resultHolder = resultHolder;
    }
}
