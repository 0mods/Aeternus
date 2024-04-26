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
    val modVersion: String by rootProject
    val modName: String by rootProject
    val minecraftVersion: String by rootProject

    archivesName.set("$modName-forge-${modVersion}_$minecraftVersion")
}

val common: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    named("developmentForge") { extendsFrom(common) }
}

dependencies {
    val forgeVersion: String by rootProject
    val kffVersion: String by rootProject
    val minecraftVersion: String by rootProject
    val architecturyApiVersion: String by rootProject

    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")

    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion") {
        include(this)
    }
    implementation("thedarkcolour:kotlinforforge:$kffVersion") {
        include(this)
    }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(path = ":common", configuration = "transformProductionForge"))
}

tasks {
    shadowJar {
        configurations = listOf(shadowBundle)
        archiveClassifier = "dev-shadow"
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
    }
}
