package team.zeromods.ancientmagic.item;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.MagicTypes;

public class ManaStorage extends MagicItem {
    public ManaStorage(MagicBuilder builder, int manaCount, boolean fireProof) {
        super(builder.setMagicSubtype(MagicTypes.STORAGE).setMaxMana(manaCount));

        if (fireProof) builder.fireProof();
    }
}
