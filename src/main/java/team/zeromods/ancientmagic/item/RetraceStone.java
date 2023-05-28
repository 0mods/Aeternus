package team.zeromods.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.IMagicType;
import api.ancientmagic.magic.MagicType;
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
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public IMagicType getMagicType() {
        return MagicType.PRE_HIGH_MAGIC;
    }

    @Override
    public int maxMana() {
        return 0;
    }

    @Override
    public void stateFunction(Level level, Player player, InteractionHand hand) {}

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return CompactInitializer.getCuriosLoaded() ? AMCurio.RetraceStoneEventProvider : super.initCapabilities(stack, nbt);
    }
}
