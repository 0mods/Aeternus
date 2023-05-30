package team.zeromods.ancientmagic;

import api.ancientmagic.mod.Constant;
import net.minecraftforge.fml.common.Mod;
import team.zeromods.ancientmagic.event.call.EventRegister;

@Mod(Constant.Key)
public class AncientMagic {
    public AncientMagic() {
        Constant.LOGGER.debug("Starting Mod!");
        EventRegister.init();
    }
}
