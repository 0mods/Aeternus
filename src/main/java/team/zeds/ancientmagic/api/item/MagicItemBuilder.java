package team.zeds.ancientmagic.api.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import team.zeds.ancientmagic.api.magic.MagicType;
import team.zeds.ancientmagic.api.magic.MagicType.MagicClassifier;
import team.zeds.ancientmagic.api.magic.MagicTypes;

public class MagicItemBuilder {
    private final Item.Properties properties;

    private MagicType magicType;
    private MagicType magicSubtype;

    private int maxMana;
    private int subManaIfUse;

    private MagicItemBuilder() {
        this.properties = new Item.Properties();
        this.magicType = MagicTypes.LOW_MAGIC;
        this.magicSubtype = MagicTypes.NOTHING;
        this.maxMana = 0;
        this.subManaIfUse = 1;
    }

    public MagicItemBuilder setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        return this;
    }

    public MagicItemBuilder setManaSub(int count) {
        this.subManaIfUse = count;
        return this;
    }

    public MagicItemBuilder fireProof() {
        properties.fireResistant();
        return this;
    }

    public MagicItemBuilder setFood(FoodProperties properties) {
        this.properties.food(properties);
        return this;
    }

    public MagicItemBuilder setMagicType(MagicType type) {
        if (type.getClassifier() == MagicClassifier.MAIN_TYPE) {
            this.magicType = type;
            return this;
        }
        else throw new RuntimeException(String.format("Classifier %s isn't %s. Accepts only %s!", type.getClassifier()
                .describeConstable(), MagicClassifier.MAIN_TYPE, MagicClassifier.MAIN_TYPE));
    }

    public MagicItemBuilder setMagicSubtype(MagicType type) {
        if (type.getClassifier() == MagicClassifier.SUBTYPE) {
            this.magicSubtype = type;
            return this;
        }
        else throw new RuntimeException(String.format("Classifier %s isn't %s. Accepts only %s!", type.getClassifier()
                .describeConstable(), MagicClassifier.SUBTYPE, MagicClassifier.SUBTYPE));
    }

    public MagicItemBuilder setRarity(Rarity rarity) {
        properties.rarity(rarity);
        return this;
    }

    public MagicItemBuilder setRemainder(Item item) {
        properties.craftRemainder(item);
        return this;
    }

    public MagicItemBuilder stacks(int stacksTo) {
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

    public static MagicItemBuilder get() {
        return new MagicItemBuilder();
    }
}
