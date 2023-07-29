package team.zeds.ancientmagic.common.api.block

import team.zeds.ancientmagic.common.api.helper.IHandleStack

@FunctionalInterface
interface IHandled {
    fun createHandler(contextChange: Runnable): IHandleStack
}
