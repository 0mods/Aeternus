package team.zeds.ancientmagic.api.cap

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull
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
    @Nullable
    fun getMagicType(): MagicType

    /**
     * Getter of [MagicType].
     * Have a [MagicClassifier] check of [MagicClassifier.SUBTYPE].
     * Can be a null.
     * @return [MagicType] or null
     */
    @Nullable
    fun getMagicSubtype(): MagicType

    /**
     * [Int] value setter with name "mana"
     * @param max is value of max mana
     */
    fun setMaxMana(@NotNull max: Int)

    /**
     * [MagicType] value setter.
     * Only [MagicClassifier.MAIN_TYPE] accepts
     * @param type is sets [MagicType]
     */
    fun setMagicType(@NotNull type: MagicType)

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

    fun getStack() : ItemStack

    fun setStack(stack: ItemStack)
}