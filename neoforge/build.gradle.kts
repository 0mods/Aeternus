plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

base {
    val modVersion: String by rootProject
    val modName: String by rootProject
    val minecraftVersion: String by rootProject

    archivesName.set("$modName-neo-${modVersion}_$minecraftVersion")
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
    named("developmentNeoForge") { extendsFrom(common) }
}

repositories {
    maven("https://maven.neoforged.net/releases")
}

dependencies {
    val kffVersion: String by rootProject
    val neoVersion: String by rootProject
    val architecturyApiVersion: String by rootProject

    neoForge("net.neoforged:neoforge:$neoVersion")

    implementation("thedarkcolour:kotlinforforge:$kffVersion")
    modImplementation("dev.architectury:architectury-neoforge:$architecturyApiVersion")

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
