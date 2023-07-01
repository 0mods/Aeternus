package team.zeds.ancientmagic;

import net.minecraftforge.fml.common.Mod;
import team.zeds.ancientmagic.api.mod.Constant;
import team.zeds.ancientmagic.init.AMManage;

@Mod(Constant.KEY)
public class AncientMagic {
    public AncientMagic() {
        Constant.LOGGER.debug("Setup a Mod!");
        AMManage.init();
    }
}
