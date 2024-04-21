import org.jetbrains.kotlin.gradle.utils.extendsFrom

//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//
//plugins {
//    eclipse
//    id("net.minecraftforge.gradle") version "[6.0,6.2)"
//    `maven-publish`
//    kotlin("jvm")
//    kotlin("plugin.serialization")
//    id("org.spongepowered.mixin") version "0.7.38"
//    id("org.parchmentmc.librarian.forgegradle") version "1.+"
//}
//
//jarJar.enable()
//
//val minecraftVersion: String by project
//val modName: String by project
//val modAuthor: String by project
//val modId: String by project
//
//base {
//    archivesName = "${modName}-forge-${minecraftVersion}"
//}
//
//mixin {
//    add(sourceSets.main.get(), "${modId}.refmap.json")
//    config("${modId}.mixins.json")
//    config("${modId}.forge.mixins.json")
//}
//
//minecraft {
//    val parchmentMCVersion: String by project
//    val parchmentVersion: String by project
//    mappings("parchment", "$parchmentVersion-$parchmentMCVersion")
//
//    copyIdeResources = true
//
//    val transformerFile = file("src/main/resources/META-INF/accesstransformer.cfg")
//    if (transformerFile.exists()) {
//        println("Founded Forge assets transformer!")
//        accessTransformer(transformerFile)
//    }
//    runs {
//        create("client") {
//            taskName = "Forge: Client (Aeternus)"
//            workingDirectory(project.file("run"))
//            ideaModule("${rootProject.name}.${project.name}.main")
//            taskName("Client")
//            property("mixin.env.remapRefMap", "true")
//            property("mixin.env.refMapRemappingFile", "$projectDir/build/createSrgToMcp/output.srg")
//            mods {
//                create("modRun") {
//                    source(sourceSets.main.get())
//                    source(project(":common").sourceSets.main.get())
//                }
//            }
//            jvmArgs("-XX:+AllowEnhancedClassRedefinition")
//        }
//
//        create("server") {
//            taskName = "Forge: Server (Aeternus)"
//            workingDirectory(project.file("run"))
//            ideaModule("${rootProject.name}.${project.name}.main")
//            taskName("Server")
//            property("mixin.env.remapRefMap", "true")
//            property("mixin.env.refMapRemappingFile", "$projectDir/build/createSrgToMcp/output.srg")
//            mods {
//                create("modServerRun") {
//                    source(sourceSets.main.get())
//                    source(project(":common").sourceSets.main.get())
//                }
//            }
//            jvmArgs("-XX:+AllowEnhancedClassRedefinition")
//        }
//
//        create("data") {
//            taskName = "Forge: Data (Aeternus)"
//            workingDirectory(project.file("run"))
//            ideaModule("${rootProject.name}.${project.name}.main")
//            args("--mod", modId, "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))
//            taskName("Data")
//            property("mixin.env.remapRefMap", "true")
//            property("mixin.env.refMapRemappingFile", "$projectDir/build/createSrgToMcp/output.srg")
//            mods {
//                create("modDataRun") {
//                    source(sourceSets.main.get())
//                    source(project(":common").sourceSets.main.get())
//                }
//            }
//        }
//    }
//}
//
//sourceSets.main.get().resources.srcDir("src/generated/resources")
//
//dependencies {
//    val kffVersion: String by project
//    val forgeVersion: String by project
//    val architecturyApiVersion: String by rootProject
//
//    minecraft("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
//    implementation("thedarkcolour:kotlinforforge:$kffVersion")
//    jarJar("thedarkcolour:kotlinforforge:$kffVersion") { jarJar.ranged(this, "[$kffVersion,)") }
//
//    implementation("dev.architectury:architectury-forge:$architecturyApiVersion")
//
//    compileOnly(project(":common"))
//}
//
//tasks {
//    withType<KotlinCompile> {
//        source(project(":common").sourceSets.main.get().allSource)
//    }
//
//    javadoc {
//        source(project(":common").sourceSets.main.get().allJava)
//    }
//
//    named("sourcesJar", Jar::class) {
//        from(project(":common").sourceSets.main.get().allSource)
//    }
//
//    processResources {
//        from(project(":common").sourceSets.main.get().resources)
//    }
//
//    jar { finalizedBy("reobfJar") }
//}
//
//publishing {
//    publications {
//        register("mavenJava", MavenPublication::class) {
//            artifactId = base.archivesName.get()
//            artifact(tasks.jar)
//            fg.component(this)
//        }
//    }
//
//    repositories {
//        maven("file://${System.getenv("local_maven")}")
//    }
//}
//
//sourceSets.forEach {
//    val dir = layout.buildDirectory.dir("sourceSets/${it.name}")
//    it.output.setResourcesDir(dir)
//    it.kotlin.destinationDirectory.set(dir)
//}

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
    val minecraftVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")
    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion")
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
