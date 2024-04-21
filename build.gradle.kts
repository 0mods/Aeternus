import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*

val minecraftVersion: String by project
val modName: String by project
val modAuthor: String by project
val coroutines_version: String by project
val serialization_version: String by project
val parchmentMCVersion: String by project
val parchmentVersion: String by project
val modId: String by project
val version: String by project
val modGroup: String by project

plugins {
    java
    idea
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.4-SNAPSHOT"
    id("io.github.pacifistmc.forgix") version "1.2.6"
    kotlin("jvm") version "1.9.23" apply false
    kotlin("plugin.serialization") version "1.9.23" apply false
}

forgix {
    val fullPath = "$modGroup.$modId"
    group = fullPath
    mergedJarName = "$modName-$version.jar"

    if (project == findProject(":fabric")) {
        val fabric = FabricContainer()
        fabric.jarLocation = "build/libs/$modName-fabric-$version.jar"
        fabricContainer = fabric
    }

    if (project == findProject(":forge")) {
        val forge = ForgeContainer()
        forge.jarLocation = "build/libs/$modName-fabric-$version.jar"
        forgeContainer = forge
    }

    if (project == findProject(":neoforge")) {
        val neo = CustomContainer()
        neo.projectName = "neoforge"
        neo.jarLocation = "build/libs/$modName-fabric-$version.jar"
        customContainers.add(neo)
    }

    removeDuplicate(fullPath)
}

// ################## NOT USED/BUG FIX CRUNCH #######################
architectury {
    minecraft = minecraftVersion
}

loom {
    accessWidenerPath.set(project(":common").file("src/main/resources/$modId.accesswidener"))
    silentMojangMappingsLicense()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())
}
// ##################### END #######################
subprojects {
    apply(plugin = "io.github.pacifistmc.forgix")
    apply(plugin = "architectury-plugin")
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    val javaVersion: String by project

    base {
        archivesName.set(modName)
    }

    architectury {
        minecraft = minecraftVersion
    }

    loom {
        silentMojangMappingsLicense()
        val fileAW = project(":common").file("src/main/resources/$modId.accesswidener")
        if (fileAW.exists()) accessWidenerPath.set(fileAW)
    }

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion.toInt()))
        withJavadocJar()
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
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:24.1.0")
        minecraft("com.mojang:minecraft:$minecraftVersion")
        @Suppress("UnstableApiUsage")
        mappings(loom.layered {
            this.officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${parchmentMCVersion}:${parchmentVersion}@zip")
        })

        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutines_version}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${coroutines_version}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${serialization_version}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${serialization_version}")
    }

    tasks {
        jar {
            manifest {
                attributes(
                        "Specification-Title" to modName,
                        "Specification-Vendor" to modAuthor,
                        "Specification-Version" to archiveVersion,
                        "Implementation-Title" to project.name,
                        "Implementation-Version" to archiveVersion,
                        "Implementation-Vendor" to modAuthor,
                        "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
                        "Timestamp" to System.currentTimeMillis(),
                        "Built-On-Java" to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                        "Build-On-Minecraft" to minecraftVersion
                )
            }

            from("LICENSE") {
                rename { "${it}_${modName}" }
            }
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(17)
        }

        withType<KotlinCompile> { compilerOptions.freeCompilerArgs.add("-Xjvm-default=all") }

        processResources {
            val modLoader: String by project; val mlVersion: String by project; val license: String by project
            val credits: String by project; val modAuthor: String by project; val mcRange: String by project
            val modName: String by project; val modId: String by project; val description: String by project
            val version: String by project; val kffRange: String by project; val fabricLoaderVersion: String by project
            val clothVersion: String by project; val forgeVersionRange: String by project; val neoVersionRange: String by project
            val klfVersion: String by project

            val replacement = mapOf(
                    "modloader" to modLoader, "mlVersion" to mlVersion, "license" to license, "modId" to modId,
                    "modVersion" to version, "modName" to modName, "credits" to credits,"modAuthor" to modAuthor,
                    "description" to description, "mcRange" to mcRange, "kffRange" to kffRange,
                    "fabricLoaderVersion" to fabricLoaderVersion, "clothVersion" to clothVersion,
                    "minecraftVersion" to minecraftVersion, "forgeVersionRange" to forgeVersionRange,
                    "neoVersionRange" to neoVersionRange, "klfVersion" to klfVersion
            )

            from(project(":common").sourceSets.main.get().resources)

            filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta", "*.mixins.json", "fabric.mod.json")) {
                expand(replacement)
            }

            inputs.properties(replacement)
        }

        build { finalizedBy(mergeJars) }
        assemble { finalizedBy(mergeJars) }
    }

    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }
}
