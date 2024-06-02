val minecraftVersion: String by project
val modName: String by project
val modId: String by project

val enabledPlatforms: String by project

architectury {
    common(enabledPlatforms.split(","))
}

loom {
    mixin {
        useLegacyMixinAp = true
        add(sourceSets.main.get(), "$modId.refmap.json")
    }
}

dependencies {
    val architecturyApiVersion: String by rootProject
    modApi("dev.architectury:architectury:$architecturyApiVersion")
}

publishing {
    publications {
        create<MavenPublication>("mavenCommon") {
            artifactId = base.archivesName.get()
            from(components["kotlin"])
        }
    }

    repositories {
        val mk = System.getenv("MAVEN_KEY")
        val mp = System.getenv("MAVEN_PASS")
        val releaseType: String by rootProject
        val artefact = if (releaseType.isEmpty()) "releases" else "snapshots"

        logger.info("MAVEN_KEY: $mk")
        logger.info("MAVEN_PASS: $mp")

        if (mk != null && mp != null) {
            maven("https://maven.0mods.team/$artefact/") {
                credentials {
                    username = mk
                    password = mp
                }
            }
        }
    }
}
