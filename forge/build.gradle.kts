plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val modId: String by rootProject

loom {
    forge {
        convertAccessWideners = true
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
        mixinConfigs("$modId.mixins.json")
    }
}

architectury {
    platformSetupLoomIde()
    forge()
}

base {
    val modVersion = rootProject.file("VERSION").readText().trim()
    val modName: String by rootProject
    val minecraftVersion: String by rootProject

    archivesName.set("$modName-forge-${minecraftVersion}_$modVersion")
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

    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")

    modImplementation("dev.architectury:architectury-forge:$architecturyApiVersion") { include(this) }
    implementation("thedarkcolour:kotlinforforge:$kffVersion") { include(this) }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    common(project(path = ":forgelike", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) { isTransitive = false }
    shadowCommon(project(path = ":forgelike", configuration = "transformProductionForge")) { isTransitive = false }
}

tasks {
    shadowJar {
        configurations = listOf(shadowCommon)
        archiveClassifier = "dev-shadow"
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
    }
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
