package team.zeds.ancientmagic.fabric.capability;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import team.zeds.ancientmagic.common.api.cap.PlayerMagic;
import team.zeds.ancientmagic.common.api.magic.type.MagicType;
import team.zeds.ancientmagic.common.api.magic.type.MagicTypes;
import team.zeds.ancientmagic.fabric.network.AMNetwork;
import team.zeds.ancientmagic.fabric.util.EntityDataHolder;

public class PlayerMagicCapability implements PlayerMagic<PlayerMagicCapability> {
    protected int level;
    private static PlayerMagicCapability cap;
    private final Player player;
    private final EntityDataHolder holder;

    protected PlayerMagicCapability(Player player) {
        this.player = player;
        this.holder = (EntityDataHolder) player;
    }

    @NotNull
    public static PlayerMagicCapability getInstance(Player player) {
        if (cap == null) cap = new PlayerMagicCapability(player);
        return cap;
    }

    @Override
    public int getMagicLevel() {
        return this.level;
    }

    @NotNull
    @Override
    public MagicType getMagicType() {
        return MagicTypes.getByNumeration(this.level);
    }

    @Override
    public void addLevel(int count) {
        this.level = Math.min(this.level + count, 4);
        this.save(holder);
        syncMagic(this.level, (ServerPlayer) this.player);
    }

    @Override
    public void subLevel(int count) {
        this.level = Math.max(this.level - count, 0);
        this.save(holder);
        syncMagic(this.level, (ServerPlayer) this.player);
    }

    @Override
    public void setLevel(int to) {
        this.level = to;
        this.save(holder);
        syncMagic(this.level, (ServerPlayer) this.player);
    }

    @Override
    public void copy(@NotNull PlayerMagicCapability source) {
        this.level = source.getMagicLevel();
    }

    public void save(EntityDataHolder holder) {
        holder.getPersistentData().putInt("AncientMagicTag.level", this.level);
        holder.getPersistentData().merge(this.save());
    }

    public void load(EntityDataHolder holder) {
        this.level = holder.getPersistentData().getInt("AncientMagicTag.level");
    }

    public void syncMagic(int level, ServerPlayer player) {
        FriendlyByteBuf byteBuf = PacketByteBufs.create();
        byteBuf.writeInt(level);
        ServerPlayNetworking.send(player, AMNetwork.C2S_PLAYER_MAGIC_ID, byteBuf);
    }
}
