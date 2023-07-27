package team.zeds.ancientmagic.api.block

import team.zeds.ancientmagic.api.helper.IHandleStack

@FunctionalInterface
interface IHandled {
    fun createHandler(contextChange: Runnable): IHandleStack
}