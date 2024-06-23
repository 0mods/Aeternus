val modId: String by project

architectury {
    forgeLike(listOf("forge", "neoforge")) {
        platformPackage("neoforge", "forge")
        remapForgeLike("net/minecraftforge/common/ForgeConfigSpec", "net/neoforged/neoforge/common/ModConfigSpec")
    }
}

dependencies {
    val minecraftVersion: String by rootProject
    val forgeVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")
    compileOnly(project(":common"))
    modImplementation("ru.hollowhorizon:hollowcore:1.21-1.0.0")

    include("io.github.classgraph:classgraph:4.8.173")
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = base.archivesName.get()
            from(components["kotlin"])
        }
    }

    repositories {
        val mk = System.getenv("MAVEN_KEY")
        val mp = System.getenv("MAVEN_PASS")

        if (mk != null && mp != null) {
            maven("http://maven.0mods.team") {
                credentials {
                    username = mk
                    password = mp
                }
            }
        }
    }
}
