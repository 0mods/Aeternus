package api.ancientmagic.item;

import api.ancientmagic.magic.IMagicType;
import api.ancientmagic.magic.MagicState;
import api.ancientmagic.mod.Constant;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagicItem extends Item implements MagicState {
    protected static int maxStorageMana;
    protected final MagicBuilder builder;

    public MagicItem(MagicBuilder builder) {
        super(builder.getProperties());
        this.builder = builder;
        this.builder.getProperties().setNoRepair();
    }

    @Override
    public void consumeMana(int numberOfConsume, ItemStack stack) {
        var startMana = this.getStorageMana(stack);
        var consumedMana = startMana - numberOfConsume;
        if (startMana != 0) {
            stack.getOrCreateTag().putInt("ManaStorage", consumedMana);
            var dmg = stack.getDamageValue();
            if (dmg != 1) {
                --dmg;
                stack.setDamageValue(dmg);
            }
        } else stack.getOrCreateTag().putInt("ManaStorage", 0);
    }

    @Override
    public void addMana(int countOfAddition, ItemStack stack) {
        var storageMana = this.getStorageMana(stack);
        var additionMana = storageMana + countOfAddition;
        if (storageMana < this.maxMana(stack)) {
            stack.getOrCreateTag().putInt("ManaStorage", additionMana);
            int damageValue = stack.getDamageValue();
            ++damageValue;
            stack.setDamageValue(damageValue);
        }
    }

    @Override
    public int getStorageMana(ItemStack stack) {
        if (this.builder.getMaxMana(stack) != 0) {
            var dmg = this.builder.getManaCount(stack) + 1;
            stack.setDamageValue(dmg);
        }
        return this.builder.getManaCount(stack);
    }

    @Override
    public int maxMana(ItemStack stack) {
        stack.getOrCreateTag().putInt("MaxManaStorage", maxStorageMana);
        stack.getOrCreateTag().putInt("ManaStorage", 0);
        return stack.getOrCreateTag().getInt("MaxManaStorage");
    }

    @Override
    public IMagicType getMagicType() {
//        if (this.builder.getMagicType() != null) {
//            return this.builder.getMagicType();
//        } else {
//            throw new RuntimeException("Magic type is not initialized!");
//        }

        if (this.builder.getMagicType() != null) {
            if (this.builder.getMagicType().getClassifier() == IMagicType.MagicClassifier.MAIN_TYPE) return this.builder.getMagicType();
            else {
                Constant.LOGGER.error(String.format("Magic Type %s is not main type", this.builder.getMagicType().getName()));
                throw new RuntimeException(String.format("Magic type %s is not main type!", this.builder.getMagicType().getName()));
            }
        } else return null;
    }

    @Override
    public IMagicType getMagicSubtype() {
        if (this.builder.getMagicSubtype() != null) {
            if (this.builder.getMagicSubtype().getClassifier() == IMagicType.MagicClassifier.SUBTYPE) return this.builder.getMagicSubtype();
            else {
                Constant.LOGGER.error(String.format("Magic Type %s is not subtype", this.builder.getMagicSubtype().getName()));
                throw new RuntimeException(String.format("Magic type %s is not subtype!", this.builder.getMagicSubtype().getName()));
            }
        } else return null;
    }

    @Override
    public void onActive(Level level, Player player, InteractionHand hand) {}

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player,
                                                           @NotNull InteractionHand hand) {
        var stack = player.getItemInHand(hand);

        if (!stack.is(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (this.maxMana(stack) != 0 && this.getStorageMana(stack) != 0) {
            this.consumeMana(1,stack);
            this.onActive(level, player, hand);
            return InteractionResultHolder.success(stack);
        } else if (this.maxMana(stack) != 0 && this.getStorageMana(stack) == 0) {
            this.setDamage(stack, 0);
            player.sendSystemMessage(this.getMagicType().getMagicTooltip("notMana", this.getName(stack)));
            return InteractionResultHolder.fail(stack);
        } else if (this.maxMana(stack) == 0) {
            this.onActive(level, player, hand);
        }
        return InteractionResultHolder.pass(stack);
    }

    public static class MagicBuilder {
        private Properties properties = new Properties();
        private static MagicBuilder instance;
        private IMagicType magicType;
        private IMagicType magicSubtype;

        private MagicBuilder() {
            instance = this;
        }

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
            return instance != null ? instance : new MagicBuilder();
        }

        public MagicBuilder setMaxMana(int maxMana, MagicItem item) {
            maxStorageMana = maxMana;
            ItemStack stack = new ItemStack(item);
            stack.getOrCreateTag().putInt("MaxManaStorage", maxMana);
            return this;
        }

        public int getMaxMana(ItemStack stack) {
            return stack.getOrCreateTag().getInt("MaxManaStorage");
        }

        private int getManaCount(ItemStack stack) {
            return stack.getOrCreateTag().getInt("ManaStorage");
        }

        public MagicBuilder setUnFlammable() {
            this.getProperties().fireResistant();
            return this;
        }

        public MagicBuilder setFood(FoodProperties properties) {
            this.getProperties().food(properties);
            return this;
        }

        public MagicBuilder setMagicType(IMagicType type) {
            this.magicType = type;
            return this;
        }

        public IMagicType getMagicType() {
            return this.magicType;
        }

        public MagicBuilder setMagicSubtype(IMagicType type) {
            if (this.getMagicType() != null) {
                this.magicSubtype = type;
                return this;
            } else throw new RuntimeException("Main magic type is null!");
        }

        public IMagicType getMagicSubtype() {
            return this.magicSubtype;
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
