@file:Suppress("UNCHECKED_CAST")

import groovy.lang.Closure
import io.github.pacifistmc.forgix.plugin.ForgixMergeExtension.*
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.jetbrains.kotlin.gradle.plugin.ide.kotlinExtrasSerialization

val minecraftVersion: String by project
val modName: String by project
val modAuthor: String by project
val parchmentMCVersion: String by project
val parchmentVersion: String by project
val modId: String by project
val modGroup: String by project
val releaseType: String by project
val imguiVersion: String by project
val kotlinVersion: String by project
val javaVersion: String by project

val modVersion = rootProject.file("VERSION").readText().trim()

val relId = when(releaseType) {
    "alpha" -> releaseType
    "beta" -> releaseType
    "snapshot" -> releaseType
    else -> ""
}

println("Mod Version: $modVersion") // Debug

plugins {
    java
    `maven-publish`
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.6-SNAPSHOT" apply false
    id("io.github.pacifistmc.forgix") version "1.2.9"
    id("com.modrinth.minotaur") version "2.+"
    kotlin("jvm")
    kotlin("plugin.serialization")
}

architectury {
    minecraft = minecraftVersion
}

forgix {
    val fullPath = "$modGroup.$modId"
    group = fullPath
    mergedJarName = "$modName-${minecraftVersion}_$modVersion.jar"

    outputDir = "build/libs"

    when (project) {
        findProject(":fabric") -> {
            val proj = findProject(":fabric")!!

            val fabricClosure = closureOf<FabricContainer> {
                jarLocation = "build/libs/${proj.base.archivesName.get()}.jar"
            } as Closure<FabricContainer>
            fabric(fabricClosure)
        }

        findProject(":forge") -> {
            val proj = findProject(":forge")!!

            val forgeClosure = closureOf<ForgeContainer> {
                jarLocation = "build/libs/${proj.base.archivesName.get()}.jar"

                mixin("".mixin)
                mixin("forgelike".mixin)
                mixin("forge".mixin)
            } as Closure<ForgeContainer>
            forge(forgeClosure)
        }

        findProject(":neoforge") -> {
            val proj = findProject(":neoforge")!!

            val neoClosure = closureOf<NeoForgeContainer> {
                jarLocation = "build/libs/${proj.base.archivesName.get()}.jar"
                mixin("".mixin)
                mixin("forgelike".mixin)
                mixin("neo".mixin)
            } as Closure<NeoForgeContainer>

            neoforge(neoClosure)
        }
    }
}

subprojects {
    apply(plugin = "architectury-plugin")
    apply(plugin = "dev.architectury.loom")

    val loom: LoomGradleExtensionAPI = project.extensions.getByName<LoomGradleExtensionAPI>("loom")

    loom.apply {
        silentMojangMappingsLicense()
        val fileAW = project(":common").file("src/main/resources/$modId.accesswidener")
        if (fileAW.exists()) accessWidenerPath.set(fileAW)
    }

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
        withSourcesJar()
    }

    repositories {
        mavenCentral()
        maven("https://maven.parchmentmc.org")
        maven("https://repo.spongepowered.org/repository/maven-public/")
        maven("https://maven.blamejared.com")
        maven("https://thedarkcolour.github.io/KotlinForForge/")
        maven("https://maven.shedaniel.me/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.terraformersmc.com/releases/")
        maven("https://jitpack.io")
        maven("https://maven.0mods.team/releases")
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
        @Suppress("UnstableApiUsage")
        "mappings"(loom.layered {
            this.officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${parchmentMCVersion}:${parchmentVersion}@zip")
        })
    }

    tasks.processResources {
        val modLoader: String by project; val mlVersion: String by project; val license: String by project
        val credits: String by project; val modAuthor: String by project; val mcRange: String by project
        val modName: String by project; val modId: String by project; val description: String by project
        val fabricLoaderVersion: String by project; val clothVersion: String by project
        val forgeVersionRange: String by project; val neoVersionRange: String by project; val klfVersion: String by project
        val architecturyApiVersion: String by project

        val replacement = mapOf(
            "modloader" to modLoader, "mlVersion" to mlVersion, "license" to license, "modId" to modId,
            "modVersion" to modVersion, "modName" to modName, "credits" to credits,"modAuthor" to modAuthor,
            "description" to description, "mcRange" to mcRange,
            "fabricLoaderVersion" to fabricLoaderVersion, "clothVersion" to clothVersion,
            "minecraftVersion" to minecraftVersion, "forgeVersionRange" to forgeVersionRange,
            "neoVersionRange" to neoVersionRange, "klfVersion" to klfVersion,
            "architecturyApiVersion" to architecturyApiVersion
        )

        from(project(":common").sourceSets.main.get().resources)

        filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta", "*.mixins.json", "fabric.mod.json")) {
            expand(replacement)
        }

        inputs.properties(replacement)
    }

    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    val archName = if (relId.isNotEmpty()) "$modName-$relId.$minecraftVersion" else "$modName.$minecraftVersion"

    group = modGroup
    version = modVersion

    base {
        archivesName.set(archName.lowercase())
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:24.1.0")

        //Kotlin
        implementation(kotlin("reflect", kotlin.coreLibrariesVersion))
        implementation(kotlin("stdlib", kotlin.coreLibrariesVersion))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:+")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:+")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:+")

        //ImGui
        implementation("io.github.spair:imgui-java-binding:$imguiVersion")
        implementation("io.github.spair:imgui-java-lwjgl3:$imguiVersion")
        implementation("io.github.spair:imgui-java-natives-windows:$imguiVersion")
        implementation("io.github.spair:imgui-java-natives-linux:$imguiVersion")
        implementation("io.github.spair:imgui-java-natives-macos:$imguiVersion")

        // Effekseer Swig
        implementation("Effekseer.swig:Swig:1.0")
    }

    tasks {
        jar {
            from("LICENSE") {
                rename { "${it}_${modName}" }
            }
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(javaVersion.toInt())
        }

        compileKotlin {
            useDaemonFallbackStrategy.set(false)
            compilerOptions.freeCompilerArgs.add("-Xjvm-default=all")
        }
    }
}

tasks.build.get().finalizedBy("mergeJars")
tasks.assemble.get().finalizedBy("mergeJars")

val String.mixin
    get() = "$modId${
        if (this.isNotEmpty()) ".$this"
        else ""
    }.mixins.json"
