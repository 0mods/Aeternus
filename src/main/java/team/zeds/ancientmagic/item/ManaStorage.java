package team.zeds.ancientmagic.item;

import team.zeds.ancientmagic.api.MagicItem;
import team.zeds.ancientmagic.api.magic.MagicTypes;

public class ManaStorage extends MagicItem {
    public ManaStorage(MagicBuilder builder, int manaCount, boolean fireProof) {
        super(builder.setMagicSubtype(MagicTypes.STORAGE).setMaxMana(manaCount));

        if (fireProof) builder.fireProof();
    }
}
