plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val modName: String by rootProject

architectury {
    platformSetupLoomIde()
    fabric()
}

base {
    val modVersion: String by rootProject
    val modName: String by rootProject
    val minecraftVersion: String by rootProject

    archivesName.set("$modName-fabric-${minecraftVersion}_$modVersion")
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
    named("developmentFabric") { extendsFrom(common) }
}

dependencies {
    val fabricLoaderVersion: String by rootProject
    val fabricVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    val clothVersion: String by rootProject
    val klfVersion: String by rootProject

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion") { include(this) }
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion") { include(this) }
    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion") { include(this) }
    modImplementation("net.fabricmc:fabric-language-kotlin:$klfVersion") { include(this) }

    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothVersion") {
        exclude(group = "net.fabricmc.fabric-api")
        include(this)
    }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(path = ":common", configuration = "transformProductionFabric"))
}

tasks {
    shadowJar {
        configurations = listOf(shadowBundle)
        archiveClassifier = "dev-shadow"
        relocate("team._0mods.aeternus.api", "team._0mods.aeternus.fabric.api")
        relocate("team._0mods.aeternus.common", "team._0mods.aeternus.fabric.common")
        relocate("team._0mods.aeternus.mixin", "team._0mods.aeternus.fabric.mixin")
        relocate("team._0mods.aeternus.service", "team._0mods.aeternus.fabric.service")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
    }
}
