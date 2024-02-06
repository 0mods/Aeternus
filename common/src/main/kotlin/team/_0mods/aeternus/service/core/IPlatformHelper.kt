package team._0mods.aeternus.service.core

interface IPlatformHelper {
    fun isProduction(): Boolean

    fun isLogicalClient(): Boolean

    fun isLogicalServer(): Boolean = !isLogicalClient()

    fun isPhysicalClient(): Boolean

    fun isPhysicalServer(): Boolean = !isPhysicalClient()

    fun isModLoaded(modId: String): Boolean

    fun getModNameByModId(modId: String): String
}