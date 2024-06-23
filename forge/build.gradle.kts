plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val modId: String by rootProject

loom {
    forge {
        convertAccessWideners = true
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
        mixinConfig("$modId.mixins.json")
        mixinConfig("$modId.forgelike.mixins.json")
        mixinConfig("$modId.forge.mixins.json")
    }
}

architectury {
    platformSetupLoomIde()
    forge()
}

base {
    archivesName.set("${archivesName.get()}-forge")
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    named("developmentForge").get().extendsFrom(common)
}

dependencies {
    val forgeVersion: String by rootProject
    val kffVersion: String by rootProject
    val minecraftVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    val imguiVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project

    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")

    modImplementation("ru.hollowhorizon:hollowcore:1.21-1.0.0")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    common(project(path = ":forgelike", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) { isTransitive = false }
    shadowCommon(project(path = ":forgelike", configuration = "transformProductionForge")) { isTransitive = false }
}

tasks {
    shadowJar {
        configurations = listOf(shadowCommon)
        archiveClassifier = "dev-shadow"
        relocate("team._0mods.aeternus.platformredirect", "team._0mods.aeternus.forge")
        relocate("team._0mods.aeternus.service", "team._0mods.aeternus.forge.service")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar.get())
        archiveClassifier.set(null as String?)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenForge") {
            artifactId = "${base.archivesName.get()}-forge"
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
