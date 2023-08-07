package team.zeds.ancientmagic.common

import team.zeds.ancientmagic.common.platform.AMServices

object AMCommonInit {
    @JvmStatic
    fun init() {
        AMServices.PLATFORM.getIAMMultiblocks().init()
    }
}