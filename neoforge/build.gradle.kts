import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

archivesName.set("${archivesName.get()}-neoforge")

architectury {
    platformSetupLoomIde()
    neoForge()
}

val common: Configuration by configurations.creating
val forgeLike: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(common, forgeLike)
    runtimeClasspath.get().extendsFrom(common, forgeLike)
    named("developmentNeoForge").get().extendsFrom(common)
    named("developmentForgeLike").get().extendsFrom(forgeLike)
}

repositories {
    maven("https://maven.neoforged.net/releases")
}

dependencies {
    val kffVersion: String by rootProject
    val neoVersion: String by rootProject
    val architecturyApiVersion: String by rootProject

    neoForge("net.neoforged:neoforge:$neoVersion")

    implementation("thedarkcolour:kotlinforforge:$kffVersion") { include(this) }
    modImplementation("dev.architectury:architectury-neoforge:$architecturyApiVersion") { include(this) }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    forgeLike(project(path = ":forgelike", configuration = "namedElements"))
    shadowCommon(project(path = ":common", configuration = "transformProductionNeoForge")) { isTransitive = false }
    shadowCommon(project(path = ":forgelike", configuration = "transformProductionNeoForge")) { isTransitive = false }
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
        val releaseType: String by rootProject
        val artefact = if (releaseType.isEmpty()) "releases" else "snapshots"

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
