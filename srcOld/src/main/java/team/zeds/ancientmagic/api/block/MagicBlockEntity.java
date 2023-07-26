package team.zeds.ancientmagic.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.zeds.ancientmagic.api.mod.AMConstant;
import team.zeds.ancientmagic.capability.BlockManaCapability;
import team.zeds.ancientmagic.init.registries.AMCapability;

public abstract class MagicBlockEntity extends BlockEntity implements MenuProvider {
    private LazyOptional<BlockManaCapability> MANA_CAPABILITY = LazyOptional.empty();

    public MagicBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        MANA_CAPABILITY = LazyOptional.of(AMConstant.BLOCK_MANA_CAPABILITY::createCap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.MANA_CAPABILITY.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.putInt("am_magic_block.mana_storage", AMConstant.BLOCK_MANA_CAPABILITY.createCap().getManaStorage());
        super.saveAdditional(p_187471_);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == AMCapability.PLAYER_MAGIC_HANDLER) MANA_CAPABILITY.cast();

        return super.getCapability(cap, side);
    }
}
