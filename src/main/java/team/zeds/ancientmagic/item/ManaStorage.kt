package team.zeds.ancientmagic.item

import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.item.MagicItemBuilder
import team.zeds.ancientmagic.api.magic.MagicTypes

class ManaStorage(builder: MagicItemBuilder, manaCount: Int, fireProof: Boolean) :
    MagicItem(builder.setMagicSubtype(MagicTypes.STORAGE).setMaxMana(manaCount)) {
    init {
        if (fireProof) builder.fireProof()
    }
}
