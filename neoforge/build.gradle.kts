plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm")
    kotlin("plugin.serialization")
}

architectury {
    platformSetupLoomIde()
    neoForge()
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
