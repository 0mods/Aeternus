package team.zeds.ancientmagic.api.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import team.zeds.ancientmagic.api.magic.MagicType;
import team.zeds.ancientmagic.api.magic.MagicType.MagicClassifier;
import team.zeds.ancientmagic.api.magic.MagicTypes;

public class MagicItemBuilderOld {
    private final Item.Properties properties;

    private MagicType magicType;
    private MagicType magicSubtype;

    private int maxMana;
    private int subManaIfUse;

    private MagicItemBuilderOld() {
        this.properties = new Item.Properties();
        this.magicType = MagicTypes.LOW_MAGIC;
        this.magicSubtype = MagicTypes.NOTHING;
        this.maxMana = 0;
        this.subManaIfUse = 1;
    }

    public MagicItemBuilderOld setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        return this;
    }

    public MagicItemBuilderOld setManaSub(int count) {
        this.subManaIfUse = count;
        return this;
    }

    public MagicItemBuilderOld fireProof() {
        properties.fireResistant();
        return this;
    }

    public MagicItemBuilderOld setFood(FoodProperties properties) {
        this.properties.food(properties);
        return this;
    }

    public MagicItemBuilderOld setMagicType(MagicType type) {
        if (type.getClassifier() == MagicClassifier.MAIN_TYPE) {
            this.magicType = type;
            return this;
        }
        else throw new RuntimeException(String.format("Classifier %s isn't %s. Accepts only %s!", type.getClassifier()
                .describeConstable(), MagicClassifier.MAIN_TYPE, MagicClassifier.MAIN_TYPE));
    }

    public MagicItemBuilderOld setMagicSubtype(MagicType type) {
        if (type.getClassifier() == MagicClassifier.SUBTYPE) {
            this.magicSubtype = type;
            return this;
        }
        else throw new RuntimeException(String.format("Classifier %s isn't %s. Accepts only %s!", type.getClassifier()
                .describeConstable(), MagicClassifier.SUBTYPE, MagicClassifier.SUBTYPE));
    }

    public MagicItemBuilderOld setRarity(Rarity rarity) {
        properties.rarity(rarity);
        return this;
    }

    public MagicItemBuilderOld setRemainder(Item item) {
        properties.craftRemainder(item);
        return this;
    }

    public MagicItemBuilderOld stacks(int stacksTo) {
        properties.stacksTo(stacksTo);
        return this;
    }

    public int getMaxMana() {
        if (maxMana >= 0)
            return this.maxMana;
        else throw new RuntimeException(String.format("%s may not be a lower than 0", maxMana));
    }

    public int getSubMana() {
        return subManaIfUse;
    }

    public MagicType getMagicType() {
        return magicType;
    }

    public MagicType getMagicSubtype() {
        return magicSubtype;
    }

    public Item.Properties getProperties() {
        return properties;
    }

    public static MagicItemBuilderOld get() {
        return new MagicItemBuilderOld();
    }
}
