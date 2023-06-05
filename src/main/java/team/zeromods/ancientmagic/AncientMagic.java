package team.zeromods.ancientmagic;

import api.ancientmagic.mod.Constant;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import team.zeromods.ancientmagic.init.AMManage;
import team.zeromods.ancientmagic.init.AMNetwork;

@Mod(Constant.Key)
public class AncientMagic {
    public AncientMagic() {
        Constant.LOGGER.debug("Starting Mod!");
        AMManage.init();
    }
}
