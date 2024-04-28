import dev.architectury.plugin.ArchitectPluginExtension
import groovy.lang.Closure
import io.github.pacifistmc.forgix.plugin.ForgixMergeExtension.*
import net.fabricmc.loom.api.LoomGradleExtensionAPI

val minecraftVersion: String by project
val modName: String by project
val modAuthor: String by project
val coroutines_version: String by project
val serialization_version: String by project
val parchmentMCVersion: String by project
val parchmentVersion: String by project
val modId: String by project
val modGroup: String by project

plugins {
    java
    idea
    id("architectury-plugin") version "3.4-SNAPSHOT" apply false
    id("dev.architectury.loom") version "1.4-SNAPSHOT" apply false
    id("io.github.pacifistmc.forgix") version "1.2.6"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}

forgix {
    val modVersion: String by project
    val fullPath = "$modGroup.$modId"
    group = fullPath
    mergedJarName = "$modName-${minecraftVersion}_$modVersion.jar"

    outputDir = "build/libs"

    if (project == findProject(":fabric")) {
        val fabricClosure = closureOf<FabricContainer> {
            jarLocation = "build/libs/$modName-fabric-${minecraftVersion}_$modVersion.jar"
        } as Closure<FabricContainer>
        fabric(fabricClosure)
    }

    if (project == findProject(":forge")) {
        val forgeClosure = closureOf<ForgeContainer> {
            jarLocation = "build/libs/$modName-forge-${minecraftVersion}_$modVersion.jar"
        } as Closure<ForgeContainer>
        forge(forgeClosure)
    }

//    removeDuplicate(fullPath)
}

subprojects {
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
            from("LICENSE") {
                rename { "${it}_${modName}" }
            }
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(17)
        }

        compileKotlin {
            useDaemonFallbackStrategy.set(false)
            compilerOptions.freeCompilerArgs.add("-Xjvm-default=all")
        }

        processResources {
            val modLoader: String by project; val mlVersion: String by project; val license: String by project
            val credits: String by project; val modAuthor: String by project; val mcRange: String by project
            val modName: String by project; val modId: String by project; val description: String by project
            val modVersion: String by project; val kffRange: String by project; val fabricLoaderVersion: String by project
            val clothVersion: String by project; val forgeVersionRange: String by project; val neoVersionRange: String by project
            val klfVersion: String by project; val architecturyApiVersion: String by project

            val replacement = mapOf(
                    "modloader" to modLoader, "mlVersion" to mlVersion, "license" to license, "modId" to modId,
                    "modVersion" to modVersion, "modName" to modName, "credits" to credits,"modAuthor" to modAuthor,
                    "description" to description, "mcRange" to mcRange, "kffRange" to kffRange,
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
    }

    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }
}

tasks {
    build { finalizedBy(mergeJars) }
    assemble { finalizedBy(mergeJars) }
}

val Project.loom: LoomGradleExtensionAPI get() = (this as ExtensionAware).extensions.getByName("loom") as LoomGradleExtensionAPI

inline fun Project.architectury(noinline conf: ArchitectPluginExtension.() -> Unit) = configure<ArchitectPluginExtension>(conf)

inline fun Project.loom(noinline conf: LoomGradleExtensionAPI.() -> Unit) = configure(conf)

fun DependencyHandler.minecraft(depAnnot: Any): Dependency? = add("minecraft", depAnnot)

fun DependencyHandler.mappings(depAnnot: Any): Dependency? = add("mappings", depAnnot)
