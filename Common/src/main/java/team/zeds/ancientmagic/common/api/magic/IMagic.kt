package team.zeds.ancientmagic.common.api.magic

import org.jetbrains.annotations.Nullable
import team.zeds.ancientmagic.common.api.magic.type.MagicType
import team.zeds.ancientmagic.common.api.magic.type.MagicTypes

interface IMagic {
    fun getMagicType(): MagicType = MagicTypes.LOW_MAGIC
    @Nullable
    fun getMagicSubtype(): MagicType? = MagicTypes.ADMIN
}