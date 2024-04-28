architectury {
    forgeLike(listOf("forge", "neoforge")) {
        platformPackage("neoforge", "forge")
    }
}

dependencies {
    val minecraftVersion: String by rootProject
    val forgeVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")
    modImplementation(project(":common"))
    modImplementation("dev.architectury:architectury:$architecturyApiVersion")
}
