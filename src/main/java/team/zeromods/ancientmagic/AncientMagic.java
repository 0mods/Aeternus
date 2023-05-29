package team.zeromods.ancientmagic;

import api.ancientmagic.mod.Constant;
import api.ancientmagic.register.RegisterBase;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import team.zeromods.ancientmagic.config.CommonConfiguration;
import team.zeromods.ancientmagic.init.AMTags;
import team.zeromods.ancientmagic.event.call.EventRegister;

@Mod(Constant.Key)
public class AncientMagic {
    public AncientMagic() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.SPEC.build());

        Constant.LOGGER.debug("Starting Mod!");

        AMTags.init();
        RegisterBase.init();
        EventRegister.init();
    }
}
