package api.ancientmagic.block;

import api.ancientmagic.atomic.AtomicUse;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicType.MagicClassifier;
import api.ancientmagic.magic.MagicTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
public class MagicBlock extends Block implements EntityBlock, IMagicBlock {
    protected final MagicBuilder builder;
    protected final BlockEntityType<? extends MagicBlockEntity> blockEntity;

    public MagicBlock(MagicBuilder builder) {
        super(builder.getProperties());
        this.builder = builder;
        this.blockEntity = builder.getBlockEntityType();
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult result) {
        AtomicUse<?> atomicUse = new AtomicUse<>(state, level, pos, player, hand, result);
        this.use(atomicUse);
        return atomicUse.getReturnResult();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        if (this.blockEntity != null)
            return new MagicBlockEntity((BlockEntityType<MagicBlockEntity>) this.blockEntity, p_153215_, p_153216_);
        else return null;
    }

    @Override
    public void use(AtomicUse<?> use) {}

//    @Override
//    @NotNull
//    public MagicType getMagicType() {
//        if (this.builder.getMagicType() != null) {
//            if (this.builder.getMagicType().getClassifier() == MagicClassifier.MAIN_TYPE)
//                return this.builder.getMagicType();
//            else {
//                Constant.LOGGER.error(String.format("Magic Type %s is not main type", this.builder.getMagicType().getId()));
//                throw new RuntimeException(String.format("Magic type %s is not main type!", this.builder.getMagicType().getId()));
//            }
//        } else return MagicTypes.LOW_MAGIC;
//    }
//
//    @Override
//    @Nullable
//    public MagicType getMagicSubtype() {
//        if (this.builder.getMagicSubtype() != null) {
//            if (this.builder.getMagicSubtype().getClassifier() == MagicClassifier.SUBTYPE)
//                return this.builder.getMagicSubtype();
//            else {
//                Constant.LOGGER.error(String.format("Magic Type %s is not subtype", this.builder.getMagicSubtype().getId()));
//                throw new RuntimeException(String.format("Magic type %s is not subtype!", this.builder.getMagicSubtype().getId()));
//            }
//        } else return null;
//    }
//
//    @Override
//    public int getMaxMana(ItemStack stack, MagicBlockEntity entity) {
//        return this.builder.getMaxMana(entity);
//    }
//
//    @Override
//    public void consumeMana(int numberOfConsume, ItemStack stack, MagicBlockEntity entity) {
//        var startMana = this.getStorageMana(null, entity);
//
//        if (startMana != 0) {
//            var consumed = startMana - numberOfConsume;
//            entity.getUpdateTag().putInt("ManaStorage", consumed);
//        } else entity.getUpdateTag().putInt("ManaStorage", 0);
//    }
//
//    @Override
//    public int getStorageMana(ItemStack stack, MagicBlockEntity entity) {
//        return this.builder.getManaCount(entity);
//    }
//
//    @Override
//    public void addMana(int countOfAddition, ItemStack stack, MagicBlockEntity entity) {
//        var storageMana = this.getStorageMana(null, entity);
//        var additionMana = storageMana + countOfAddition;
//        if (storageMana < this.getMaxMana(null, entity))
//            entity.getUpdateTag().putInt("ManaStorage", additionMana);
//    }
//
//    @Override
//    public void onActive(Level level, Player player, InteractionHand hand) {}

    @SuppressWarnings({"unused", "DeprecatedIsStillUsed"})
    public static class MagicBuilder {
        private Properties properties = Properties.of();
        private MagicType magicType = MagicTypes.LOW_MAGIC;
        private MagicType magicSubtype;
        private BlockEntityType<? extends MagicBlockEntity> blockEntity;

        public static MagicBuilder get() {
            return new MagicBuilder();
        }

        @Deprecated
        public MagicBuilder setProperties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public MagicBuilder make() {
            return this.setProperties(Properties.of());
        }

        public MagicBuilder make(BlockBehaviour copyFrom) {
            return this.setProperties(Properties.copy(copyFrom));
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

        public MagicBuilder noLoot() {
            this.getProperties().noLootTable();
            return this;
        }

        @Deprecated
        public MagicBuilder drops(Block block) {
            this.getProperties().dropsLike(block);
            return this;
        }

        public MagicBuilder drops(Supplier<Block> blockIn) {
            this.getProperties().lootFrom(blockIn);
            return this;
        }

        public MagicBuilder ignitedByLava() {
            this.getProperties().ignitedByLava();
            return this;
        }

        public MagicBuilder liquid() {
            this.getProperties().liquid();
            return this;
        }

        public MagicBuilder forceSolidOn() {
            this.getProperties().forceSolidOn();
            return this;
        }

        @Deprecated
        public MagicBuilder forceSolidOff() {
            this.getProperties().forceSolidOff();
            return this;
        }

        public MagicBuilder onPush(PushReaction reaction) {
            this.getProperties().pushReaction(reaction);
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

        public MagicBuilder color(Function<BlockState, MapColor> color) {
            this.getProperties().mapColor(color);
            return this;
        }

        public MagicBuilder color(DyeColor color) {
            this.getProperties().mapColor(color);
            return this;
        }

        public MagicBuilder color(MapColor color) {
            this.getProperties().mapColor(color);
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

        public MagicBuilder instrument(NoteBlockInstrument instrument) {
            this.getProperties().instrument(instrument);
            return this;
        }

        public MagicBuilder replace() {
            this.getProperties().replaceable();
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

        public MagicBuilder setBlockEntityType(BlockEntityType<? extends MagicBlockEntity> entityType) {
            this.blockEntity = entityType;
            return this;
        }

        @Nullable
        public BlockEntityType<? extends MagicBlockEntity> getBlockEntityType() {
            if (this.blockEntity != null)
                return this.blockEntity;
            else throw new NullPointerException(String.format("Block Entity type %s is null", this.getBlockEntityType()));
        }

        @Nullable
        public BlockEntity getBlockEntity(BlockGetter block, BlockPos pos) {
            if (this.getBlockEntityType() != null)
                return this.getBlockEntityType().getBlockEntity(block, pos);
            else throw new NullPointerException(String.format("Block Entity type %s is null", this.getBlockEntityType()));
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
