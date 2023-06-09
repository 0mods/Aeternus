package api.ancientmagic.item;

import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicTypes;
import api.ancientmagic.unstandardable.MagicObjectCapability;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicItem extends Item implements IMagicItem {
    protected final MagicBuilder builder;
    private boolean canUseItem = true;

    public MagicItem(MagicBuilder builder) {
        super(builder.getProperties());
        this.builder = builder;
        this.builder.getProperties().setNoRepair();
    }

//    @Override
//    public void consumeMana(int numberOfConsume, ItemStack stack, MagicBlockEntity entity) {
//        var startMana = this.getStorageMana(stack, null);
//
//        if (startMana != 0) {
//            var consumedMana = startMana - numberOfConsume;
//            stack.getOrCreateTag().putInt("ManaStorage", consumedMana);
//            var dmg = stack.getDamageValue();
//            if (dmg != 1) {
//                --dmg;
//                stack.setDamageValue(dmg);
//            }
//        } else stack.getOrCreateTag().putInt("ManaStorage", 0);
//    }
//
//    @Override
//    public void addMana(int countOfAddition, ItemStack stack, MagicBlockEntity entity) {
//        var storageMana = this.getStorageMana(stack, null);
//        var additionMana = storageMana + countOfAddition;
//        if (storageMana < this.getMaxMana(stack, null)) {
//            stack.getOrCreateTag().putInt("ManaStorage", additionMana);
//            int damageValue = stack.getDamageValue();
//            ++damageValue;
//            stack.setDamageValue(damageValue);
//        }
//    }
//
//    @Override
//    public int getStorageMana(ItemStack stack, MagicBlockEntity entity) {
//        if (this.builder.getMaxMana(stack) != 0) {
//            var dmg = this.builder.getManaCount(stack) + 1;
//            stack.setDamageValue(dmg);
//        }
//        return this.builder.getManaCount(stack);
//    }
//
//    @Override
//    public int getMaxMana(ItemStack stack, MagicBlockEntity entity) {
//        return this.builder.getMaxMana(stack);
//    }
//
//    @Override
//    @Nullable
//    public MagicType getMagicType() {
//        if (this.builder.getMagicType() != null) {
//            if (this.builder.getMagicType().getClassifier() == MagicType.MagicClassifier.MAIN_TYPE)
//                return this.builder.getMagicType();
//            else {
//                Constant.LOGGER.error(String.format("Magic Type %s is not main type", this.builder.getMagicType().getId()));
//                throw new RuntimeException(String.format("Magic type %s is not main type!", this.builder.getMagicType().getId()));
//            }
//        } else throw new RuntimeException("Magic type is null");
//    }
//
//    @Override
//    public MagicType getMagicSubtype() {
//        if (this.builder.getMagicSubtype() != null) {
//            if (this.builder.getMagicSubtype().getClassifier() == MagicType.MagicClassifier.SUBTYPE) return this.builder.getMagicSubtype();
//            else {
//                Constant.LOGGER.error(String.format("Magic Type %s is not subtype", this.builder.getMagicSubtype().getId()));
//                throw new RuntimeException(String.format("Magic type %s is not subtype!", this.builder.getMagicSubtype().getId()));
//            }
//        } else return null;
//    }
//
//    @Override
//    public void onActive(Level level, Player player, InteractionHand hand) {}

    @Deprecated
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player,
                                                           @NotNull InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var returnableUse = new MagicItemReturnableUse(player, level, hand);

        if (!stack.is(this))
            return InteractionResultHolder.fail(stack);

        if (this.getItemUse()) {
//            if (this.getMaxMana(stack, null) != 0 && this.getStorageMana(stack, null) != 0) {
//                this.consumeMana(1, stack, null);
//                this.onActive(level, player, hand);
//                return InteractionResultHolder.success(stack);
//            } else if (this.getMaxMana(stack, null) != 0 && this.getStorageMana(stack, null) == 0) {
//                this.setDamage(stack, 0);
//                player.displayClientMessage(MagicType.getMagicMessage("notMana", this.getName(stack)), true);
//                return InteractionResultHolder.fail(stack);
//            } else if (this.getMaxMana(stack, null) == 0) {
//                this.onActive(level, player, hand);
//            }
            if (this.getBuilder().getManaCount() != 0 && this.getBuilder().getManaCount() != 0) {
                stack.getCapability(MagicObjectCapability.Provider.MAGIC_OBJECT).ifPresent(cap -> {
                    cap.subMana(this.getBuilder().getSubManaIfUse());
                });
                this.use(returnableUse);
                return returnableUse.getReturnValue();
            } else if (this.getBuilder().getMaxMana() != 0 && this.getBuilder().getManaCount() == 0) {
                player.displayClientMessage(MagicType.getMagicMessage("notMana", this.getName(stack)), true);
                return InteractionResultHolder.fail(stack);
            } else if (this.getBuilder().getMaxMana() == 0) {
                this.use(returnableUse);
                return returnableUse.getReturnValue();
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void use(MagicItemReturnableUse returnableUse) {}

    public MagicBuilder getBuilder() {
        return this.builder;
    }

    public boolean getItemUse() {
        return this.canUseItem;
    }

    public void setItemUse(boolean canUse) {
        this.canUseItem = canUse;
    }

    @SuppressWarnings({"DeprecatedIsStillUsed", "unused"})
    public static class MagicBuilder {
        Properties properties = new Properties();
        MagicType magicType = MagicTypes.LOW_MAGIC;
        MagicType magicSubtype;
        int maxManaStorage = 0;
        int manaCount;
        int subManaIfUse;

        private MagicBuilder() {}

        @Deprecated
        public Properties getProperties() {
            return properties;
        }

        @Deprecated
        public MagicBuilder setProperties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public static MagicBuilder get() {
            return new MagicBuilder();
        }

        @NotNull
        public MagicType getMagicType() {
            return this.magicType;
        }

        @Nullable
        public MagicType getMagicSubtype() {
            return this.magicSubtype;
        }

        public int getMaxMana() {
            return this.maxManaStorage;
        }

        public int getManaCount() {
            return this.manaCount;
        }

        public int getSubManaIfUse() {
            return this.subManaIfUse;
        }

        public MagicBuilder setMaxMana(int maxMana) {
            this.maxManaStorage = maxMana;
            return this;
        }


        public MagicBuilder setManaCount(int count) {
            this.manaCount = count;
            return this;
        }

        public MagicBuilder setSubManaIfUse(int subManaIfUse) {
            this.subManaIfUse = subManaIfUse;
            return this;
        }

        public MagicBuilder fireProof() {
            this.getProperties().fireResistant();
            return this;
        }

        public MagicBuilder setFood(FoodProperties properties) {
            this.getProperties().food(properties);
            return this;
        }

        @NotNull
        public MagicBuilder setMagicType(MagicType type) {
            this.magicType = type;
            return this;
        }

        @NotNull
        public MagicBuilder setMagicSubtype(MagicType type) {
            if (type.getClassifier() == MagicType.MagicClassifier.SUBTYPE)
                this.magicSubtype = type;

            return this;
        }

        public MagicBuilder setRarity(Rarity rarity) {
            this.getProperties().rarity(rarity);
            return this;
        }

        public MagicBuilder setRemainder(Item itemToRemained) {
            this.getProperties().craftRemainder(itemToRemained);
            return this;
        }

        public MagicBuilder setStacks(int stacksTo) {
            this.getProperties().stacksTo(stacksTo);
            return this;
        }
    }
}
