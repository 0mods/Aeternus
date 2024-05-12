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

//publishing {
//    publications {
//        register("mavenJava", MavenPublication::class) {
//            artifactId = base.archivesName.get()
//            from(components["kotlin"])
//        }
//    }
//
//    repositories {
//        val mk = System.getenv("MAVEN_KEY")
//        val mp = System.getenv("MAVEN_PASS")
//
//        if (mk != null && mp != null) {
//            maven("http://maven.0mods.team") {
//                credentials {
//                    username = mk
//                    password = mp
//                }
//            }
//        }
//    }
//}
