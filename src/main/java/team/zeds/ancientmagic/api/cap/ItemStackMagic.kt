package team.zeds.ancientmagic.api.cap

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.Nullable
import team.zeds.ancientmagic.api.magic.MagicType
import team.zeds.ancientmagic.api.magic.MagicType.MagicClassifier

interface ItemStackMagic {
    /**
     * Getter of [MagicType]
     *
     * Have a [MagicClassifier] check of [MagicClassifier.MAIN_TYPE]
     *
     * @return accepts objects extends [MagicType] and classifier-only [MagicClassifier.MAIN_TYPE]
     */
    fun getMagicType(): MagicType

    /**
     * Getter of [MagicType]
     * Have a [MagicClassifier] check of [MagicClassifier.SUBTYPE]
     * @return [MagicType]
     */
    fun getMagicSubtype(): MagicType

    /**
     * [Int] value setter with name "mana"
     * @param max is value of max mana
     */
    fun setMaxMana(max: Int)

    /**
     * [MagicType] value setter.
     * Only [MagicClassifier.MAIN_TYPE] accepts
     * @param type is sets [MagicType]
     */
    fun setMagicType(type: MagicType)

    /**
     * [MagicType] value setter.
     * Only [MagicClassifier.SUBTYPE] accepts
     *
     * [setMagicSubtype] may be a null
     */
    @Nullable
    fun setMagicSubtype(type: MagicType)

    /**
     * Getter of maximal mana value
     * @return [Int]
     */
    fun getMaxMana(): Int

    /**
     * Getter of maximal mana value
     * @return [Int]
     */
    fun getStorageMana(): Int

    /**
     * Adder int-value of mana
     * @param count is adder count of mana
     */
    fun addMana(count: Int)

    /**
     * Sub int-value of mana
     * @param count is a sub count of mana
     */
    fun subMana(count: Int)

    /**
     * Serialize tag value of capability
     * @param tag is value saver
     */
    fun save(tag: CompoundTag)

    /**
     * Deserialize tag value of capability
     * @param tag is value loader
     */
    fun load(tag: CompoundTag)

    /**
     * [ItemStack] getter.
     * returns only [team.zeds.ancientmagic.api.MagicItem]
     *
     * Example usage:
     * [net.minecraftforge.common.capabilities.ICapabilityProvider.getCapability] ([team.zeds.ancientmagic.init.AMCapability.MAGIC_OBJECT]).
     * (cap -> {
     *
     *      var stack = cap.getStack()
     *      var item = (MagicItem) stack.getItem()
     *      // here your code
     * })
     *
     * @return [ItemStack]
     */
    fun getStack() : ItemStack
}