package team.zeds.ancientmagic.item

import team.zeds.ancientmagic.api.MagicItem
import team.zeds.ancientmagic.api.magic.MagicTypes


class ManaStorage(builder: MagicBuilder, manaCount: Int, fireProof: Boolean) :
    MagicItem(builder.setMagicSubtype(MagicTypes.STORAGE).setMaxMana(manaCount)) {
    init {
        if (fireProof) builder.fireProof()
    }
}
