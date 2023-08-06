package team.zeds.ancientmagic.common.platform;

import team.zeds.ancientmagic.common.AMConstant;
import team.zeds.ancientmagic.common.platform.services.IAMPlatformHelper;

import java.util.ServiceLoader;

public class AMServices {
    public static final IAMPlatformHelper PLATFORM = load(IAMPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        AMConstant.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
