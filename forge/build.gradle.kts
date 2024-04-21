plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val modId: String by rootProject

loom {
    forge {
        mixinConfigs("$modId.mixins.json")
    }
}

architectury {
    platformSetupLoomIde()
    forge()
}

val common by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

val shadowBundle by configurations.creating {
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

    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion")
    implementation("thedarkcolour:kotlinforforge:$kffVersion")

    compileOnly(project(":common"))
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
