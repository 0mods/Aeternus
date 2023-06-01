package api.ancientmagic.item;

import api.ancientmagic.block.MagicBlockEntity;
import api.ancientmagic.magic.MagicType;
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
    public void consumeMana(int numberOfConsume, ItemStack stack, MagicBlockEntity entity) {
        var startMana = this.getStorageMana(stack, null);

        if (startMana != 0) {
            var consumedMana = startMana - numberOfConsume;
            stack.getOrCreateTag().putInt("ManaStorage", consumedMana);
            var dmg = stack.getDamageValue();
            if (dmg != 1) {
                --dmg;
                stack.setDamageValue(dmg);
            }
        } else stack.getOrCreateTag().putInt("ManaStorage", 0);
    }

    @Override
    public void addMana(int countOfAddition, ItemStack stack, MagicBlockEntity entity) {
        var storageMana = this.getStorageMana(stack, null);
        var additionMana = storageMana + countOfAddition;
        if (storageMana < this.getMaxMana(stack, null)) {
            stack.getOrCreateTag().putInt("ManaStorage", additionMana);
            int damageValue = stack.getDamageValue();
            ++damageValue;
            stack.setDamageValue(damageValue);
        }
    }

    @Override
    public int getStorageMana(ItemStack stack, MagicBlockEntity entity) {
        if (this.builder.getMaxMana(stack) != 0) {
            var dmg = this.builder.getManaCount(stack) + 1;
            stack.setDamageValue(dmg);
        }
        return this.builder.getManaCount(stack);
    }

    @Override
    public int getMaxMana(ItemStack stack, MagicBlockEntity entity) {
        return this.builder.getMaxMana(stack);
    }

    @Override
    public MagicType getMagicType() {
        if (this.builder.getMagicType() != null) {
            if (this.builder.getMagicType().getClassifier() == MagicType.MagicClassifier.MAIN_TYPE)
                return this.builder.getMagicType();
            else {
                Constant.LOGGER.error(String.format("Magic Type %s is not main type", this.builder.getMagicType().getName()));
                throw new RuntimeException(String.format("Magic type %s is not main type!", this.builder.getMagicType().getName()));
            }
        } else return null;
    }

    @Override
    public MagicType getMagicSubtype() {
        if (this.builder.getMagicSubtype() != null) {
            if (this.builder.getMagicSubtype().getClassifier() == MagicType.MagicClassifier.SUBTYPE) return this.builder.getMagicSubtype();
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

        if (this.getMaxMana(stack, null) != 0 && this.getStorageMana(stack, null) != 0) {
            this.consumeMana(1, stack, null);
            this.onActive(level, player, hand);
            return InteractionResultHolder.success(stack);
        } else if (this.getMaxMana(stack, null) != 0 && this.getStorageMana(stack, null) == 0) {
            this.setDamage(stack, 0);
            player.sendSystemMessage(this.getMagicType().getMagicTooltip("notMana", this.getName(stack)));
            return InteractionResultHolder.fail(stack);
        } else if (this.getMaxMana(stack, null) == 0) {
            this.onActive(level, player, hand);
        }
        return InteractionResultHolder.pass(stack);
    }

    public static class MagicBuilder {
        private Properties properties = new Properties();
        private static MagicBuilder instance;
        private MagicType magicType;
        private MagicType magicSubtype;

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

        public MagicBuilder setMaxMana(int maxMana, ItemStack stack) {
            maxStorageMana = maxMana;
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

        public MagicBuilder setMagicType(MagicType type) {
            this.magicType = type;
            return this;
        }

        public MagicType getMagicType() {
            return this.magicType;
        }

        public MagicBuilder setMagicSubtype(MagicType type) {
            if (this.getMagicType() != null && type.getClassifier() == MagicType.MagicClassifier.SUBTYPE) {
                this.magicSubtype = type;
                return this;
            } else throw new RuntimeException("Main magic type is null or chose subtype is not subtype!");
        }

        public MagicType getMagicSubtype() {
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
