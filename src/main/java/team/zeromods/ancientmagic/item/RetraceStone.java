package team.zeromods.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.MagicTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.compact.curios.AMCurio;

public class RetraceStone extends MagicItem {
    public RetraceStone() {
        super(
                MagicBuilder.get()
                        .setMagicType(MagicTypes.PRE_HIGH_MAGIC)
                        .fireProof()
                        .setRarity(Rarity.RARE)
        );
    }

    @Override
    public void onActive(Level level, Player player, InteractionHand hand) {
        var itemstack = player.getItemInHand(hand);
        var active = getActive(itemstack);

        if (player.isShiftKeyDown()) setActive(itemstack, !active);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return getActive(p_41453_);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return CompactInitializer.getCuriosLoaded() ? AMCurio.RetraceStoneEventProvider : super.initCapabilities(stack, nbt);
    }

    public static boolean getActive(ItemStack stack) {
        var active = stack.getOrCreateTag().getBoolean("RetraceStoneIsActivated");
        return !stack.hasTag() || active;
    }

    public static void setActive(ItemStack stack, boolean active) {
        stack.getOrCreateTag().putBoolean("RetraceStoneIsActivated", active);
    }
}
