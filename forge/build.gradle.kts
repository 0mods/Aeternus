plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val modId: String by rootProject

loom {
    mixin {
        useLegacyMixinAp = true
        add(sourceSets.main.get(), "$modId-forge.refmap.json")
    }

    forge {
        convertAccessWideners = true
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
        mixinConfig("$modId.mixins.json", "$modId.forgelike.mixins.json", "$modId.forge.mixins.json")
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

    modImplementation("dev.architectury:architectury-forge:$architecturyApiVersion") { include(this) }
//    implementation("thedarkcolour:kotlinforforge:$kffVersion") { include(this) }

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
