val modId: String by project

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

base {
    archivesName.set("${archivesName.get()}-neoforge")
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
//    val kffVersion: String by rootProject
    val neoVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    val imguiVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project

    neoForge("net.neoforged:neoforge:$neoVersion")

//    implementation("thedarkcolour:kotlinforforge:$kffVersion") { include(this) }
    modImplementation("dev.architectury:architectury-neoforge:$architecturyApiVersion") { include(this) }

    include(kotlin("reflect"))
    include(kotlin("stdlib"))
    include("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    include("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${coroutinesVersion}")
    include("org.jetbrains.kotlinx:kotlinx-serialization-core:${serializationVersion}")
    include("org.jetbrains.kotlinx:kotlinx-serialization-json:${serializationVersion}")

    include("io.github.spair:imgui-java-binding:$imguiVersion")
    include("io.github.spair:imgui-java-lwjgl3:$imguiVersion")
    include("io.github.spair:imgui-java-natives-windows:$imguiVersion")
    include("io.github.spair:imgui-java-natives-linux:$imguiVersion")
    include("io.github.spair:imgui-java-natives-macos:$imguiVersion")

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
        create<MavenPublication>("mavenNeoForge") {
            artifactId = "${base.archivesName.get()}-neoforge"
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
