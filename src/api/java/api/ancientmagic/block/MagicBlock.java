package api.ancientmagic.block;

import api.ancientmagic.magic.MagicState;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicType.MagicClassifier;
import api.ancientmagic.magic.MagicTypes;
import api.ancientmagic.mod.Constant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class MagicBlock extends Block implements EntityBlock, MagicState {
    protected final MagicBuilder builder;
    protected final BlockEntityType<MagicBlockEntity> blockEntity;

    public MagicBlock(MagicBuilder builder, BlockEntityType<MagicBlockEntity> blockEntity) {
        super(builder.getProperties());
        this.builder = builder;
        this.blockEntity = blockEntity;
    }

    @Override
    public InteractionResult use(@NotNull BlockState p_60503_, @NotNull Level p_60504_, @NotNull BlockPos p_60505_,
                                 @NotNull Player p_60506_, @NotNull InteractionHand p_60507_,
                                 @NotNull BlockHitResult p_60508_) {
        return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new MagicBlockEntity(this.blockEntity, p_153215_, p_153216_);
    }

    @Override
    @Nullable
    public MagicType getMagicType() {
        if (this.builder.getMagicType() != null) {
            if (this.builder.getMagicType().getClassifier() == MagicClassifier.MAIN_TYPE)
                return this.builder.getMagicType();
            else {
                Constant.LOGGER.error(String.format("Magic Type %s is not main type", this.builder.getMagicType().getId()));
                throw new RuntimeException(String.format("Magic type %s is not main type!", this.builder.getMagicType().getId()));
            }
        } else return MagicTypes.LOW_MAGIC;
    }

    @Override
    @Nullable
    public MagicType getMagicSubtype() {
        if (this.builder.getMagicSubtype() != null) {
            if (this.builder.getMagicSubtype().getClassifier() == MagicClassifier.SUBTYPE)
                return this.builder.getMagicSubtype();
            else {
                Constant.LOGGER.error(String.format("Magic Type %s is not subtype", this.builder.getMagicSubtype().getId()));
                throw new RuntimeException(String.format("Magic type %s is not subtype!", this.builder.getMagicSubtype().getId()));
            }
        } else return null;
    }

    @Override
    public int getMaxMana(ItemStack stack, MagicBlockEntity entity) {
        return this.builder.getMaxMana(entity);
    }

    @Override
    public void consumeMana(int numberOfConsume, ItemStack stack, MagicBlockEntity entity) {
        var startMana = this.getStorageMana(null, entity);

        if (startMana != 0) {
            var consumed = startMana - numberOfConsume;
            entity.getUpdateTag().putInt("ManaStorage", consumed);
        } else entity.getUpdateTag().putInt("ManaStorage", 0);
    }

    @Override
    public int getStorageMana(ItemStack stack, MagicBlockEntity entity) {
        return this.builder.getManaCount(entity);
    }

    @Override
    public void addMana(int countOfAddition, ItemStack stack, MagicBlockEntity entity) {
        var storageMana = this.getStorageMana(null, entity);
        var additionMana = storageMana + countOfAddition;
        if (storageMana < this.getMaxMana(null, entity))
            entity.getUpdateTag().putInt("ManaStorage", additionMana);
    }

    @Override
    public void onActive(Level level, Player player, InteractionHand hand) {}

    @SuppressWarnings({"unused", "DeprecatedIsStillUsed"})
    public static class MagicBuilder {
        private Properties properties = Properties.of(Material.STONE);
        private MagicType magicType = MagicTypes.LOW_MAGIC;
        private MagicType magicSubtype;

        private MagicBuilder() {}

        public static MagicBuilder get() {
            return new MagicBuilder();
        }

        @Deprecated
        public MagicBuilder setProperties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public MagicBuilder make(Material material) {
            return this.make(material, material.getColor());
        }

        public MagicBuilder make(Material material, DyeColor color) {
            return this.make(material, color.getMaterialColor());
        }

        public MagicBuilder make(Material material, MaterialColor color) {
            return this.setProperties(Properties.of(material, color));
        }

        public MagicBuilder make(Material material, Function<BlockState, MaterialColor> color) {
            return this.setProperties(Properties.of(material, color));
        }

        @Deprecated
        public Properties getProperties() {
            return this.properties;
        }

        public MagicBuilder unCollised() {
            this.getProperties().noCollission();
            return this;
        }

        public MagicBuilder unOcculised() {
            this.getProperties().noOcclusion();
            return this;
        }

        public MagicBuilder friction(float friction) {
            this.getProperties().friction(friction);
            return this;
        }

        public MagicBuilder speedBuf(float numOfBuf) {
            this.getProperties().speedFactor(numOfBuf);
            return this;
        }

        public MagicBuilder jumpBuf(float numOfBuf) {
            this.getProperties().speedFactor(numOfBuf);
            return this;
        }

        public MagicBuilder sound(SoundType type) {
            this.getProperties().sound(type);
            return this;
        }

        public MagicBuilder light(ToIntFunction<BlockState> state) {
            this.getProperties().lightLevel(state);
            return this;
        }

        public MagicBuilder strength(float destroyTime, float explosionResistant) {
            this.getProperties().strength(destroyTime, explosionResistant);
            return this;
        }

        public MagicBuilder strength(float destroyTimeAndResistant) {
            return this.strength(destroyTimeAndResistant, destroyTimeAndResistant);
        }

        public MagicBuilder instabreak() {
            this.getProperties().instabreak();
            return this;
        }

        public MagicBuilder unbreak() {
            return this.destory(-1);
        }

        public MagicBuilder randomTick() {
            this.getProperties().randomTicks();
            return this;
        }

        public MagicBuilder dynamicShape() {
            this.getProperties().dynamicShape();
            return this;
        }

        @Deprecated
        public MagicBuilder dropsLike(Block block) {
            this.getProperties().dropsLike(block);
            return this;
        }

        public MagicBuilder dropsLike(Supplier<Block> blockIn) {
            this.getProperties().lootFrom(blockIn);
            return this;
        }

        public MagicBuilder air() {
            this.getProperties().air();
            return this;
        }

        public MagicBuilder validSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> predicate) {
            this.getProperties().isValidSpawn(predicate);
            return this;
        }

        public MagicBuilder redstoneConductor(BlockBehaviour.StatePredicate predicate) {
            this.getProperties().isRedstoneConductor(predicate);
            return this;
        }

        public MagicBuilder suffocating(BlockBehaviour.StatePredicate predicate) {
            this.getProperties().isSuffocating(predicate);
            return this;
        }

        public MagicBuilder viewBlocking(BlockBehaviour.StatePredicate predicate) {
            this.getProperties().isViewBlocking(predicate);
            return this;
        }

        public MagicBuilder postProcess(BlockBehaviour.StatePredicate predicate) {
            this.getProperties().hasPostProcess(predicate);
            return this;
        }

        public MagicBuilder emissiveRendering(BlockBehaviour.StatePredicate predicate) {
            this.getProperties().emissiveRendering(predicate);
            return this;
        }

        public MagicBuilder requiresToolForDrop() {
            this.getProperties().requiresCorrectToolForDrops();
            return this;
        }

        public MagicBuilder color(MaterialColor color) {
            this.getProperties().color(color);
            return this;
        }

        public MagicBuilder destory(float time) {
            this.getProperties().destroyTime(time);
            return this;
        }

        public MagicBuilder explosionResistance(float resistance) {
            this.getProperties().explosionResistance(resistance);
            return this;
        }

        public MagicBuilder offsetType(BlockBehaviour.OffsetType type) {
            this.getProperties().offsetType(type);
            return this;
        }

        public MagicBuilder noParticle() {
            this.getProperties().noParticlesOnBreak();
            return this;
        }

        public MagicBuilder requiredFeatures(FeatureFlag... flags) {
            this.getProperties().requiredFeatures(flags);
            return this;
        }

        public MagicBuilder setMagicType(MagicType type) {
            this.magicType = type;
            return this;
        }

        public MagicBuilder setMagicSubtype(MagicType subtype) {
            if (this.getMagicType() != null && subtype.getClassifier() == MagicClassifier.SUBTYPE) {
                this.magicSubtype = subtype;
                return this;
            } else throw new RuntimeException("Main magic type is null or chose subtype is not subtype!");
        }

        protected MagicBuilder setMaxMana(int maxMana, MagicBlockEntity entity) {
            entity.getUpdateTag().putInt("MaxManaStorage", maxMana);
            return this;
        }

        private MagicType getMagicType() {
            return magicType;
        }

        private MagicType getMagicSubtype() {
            return magicSubtype;
        }

        public int getMaxMana(MagicBlockEntity entity) {
            return entity.getUpdateTag().getInt("MaxManaStorage");
        }

        public int getManaCount(MagicBlockEntity entity) {
            return entity.getUpdateTag().getInt("ManaStorage");
        }
    }
}
