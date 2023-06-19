package team.zeromods.ancientmagic.item;

import team.zeromods.ancientmagic.api.item.MagicItem;
import team.zeromods.ancientmagic.api.magic.MagicTypes;

public class ManaStorage extends MagicItem {
    public ManaStorage(MagicBuilder builder, int manaCount, boolean fireProof) {
        super(builder.setMagicSubtype(MagicTypes.STORAGE).setMaxMana(manaCount));

        if (fireProof) builder.fireProof();
    }
}
