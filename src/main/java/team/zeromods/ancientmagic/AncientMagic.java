package team.zeromods.ancientmagic;

import net.minecraftforge.fml.common.Mod;
import team.zeromods.ancientmagic.api.mod.Constant;
import team.zeromods.ancientmagic.init.AMManage;

@Mod(Constant.KEY)
public class AncientMagic {
    public AncientMagic() {
        Constant.LOGGER.debug("Starting Mod!");
        AMManage.init();
    }
}
