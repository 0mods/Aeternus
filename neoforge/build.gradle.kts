//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//
//plugins {
//    `java-library`
//    eclipse
//    `maven-publish`
//    kotlin("jvm")
//    kotlin("plugin.serialization")
//    id("net.neoforged.gradle.userdev") version "7.0.97"
//}
//
//val parchmentMCVersion: String by project
//val parchmentVersion: String by project
//val minecraftVersion: String by project
//val modName: String by project
//val modAuthor: String by project
//val modId: String by project
//
//base {
//    archivesName.set("${modName}-neo-${minecraftVersion}")
//}
//
//val transformerFile = file("src/main/resources/META-INF/accesstransformer.cfg")
//if (transformerFile.exists()) {
//    println("Founded NeoForge assets transformer!")
//    minecraft.accessTransformers.file(transformerFile)
//}
//
//subsystems.parchment {
//    minecraftVersion(parchmentMCVersion)
//    mappingsVersion(parchmentVersion)
//}
//
//runs {
//    configureEach { modSource(project.sourceSets.main.get()) }
//
//    create("client") {
//        systemProperty("neoforge.enableGameTestNamespace", modId)
//        jvmArguments("-XX:+AllowEnhancedClassRedefinition")
//    }
//
//    create("server") {
//        systemProperty("neoforge.enabledGameTestNamespaces", modId)
//        programArgument("--nogui")
//        jvmArguments("-XX:+AllowEnhancedClassRedefinition")
//    }
//
//    create("gameTestServer") {
//        systemProperty("neoforge.enabledGameTestNamespaces", modId)
//        jvmArguments("-XX:+AllowEnhancedClassRedefinition")
//    }
//
//    create("data") {
//        programArguments(
//                "--mod", modId,
//                "--all",
//                "--output", file("src/generated/resources").absolutePath,
//                "--existing", file("src/main/resources/").absolutePath
//        )
//    }
//}
//
//sourceSets.main.get().resources.srcDir("src/generated/resources")
//
//dependencies {
//    val kffVersion: String by project
//    val neoVersion: String by project
//    val architecturyApiVersion: String by rootProject
//
//    implementation("net.neoforged:neoforge:$neoVersion")
//    implementation("thedarkcolour:kotlinforforge-neoforge:$kffVersion")
//
//    implementation("dev.architectury:architectury-neoforge:$architecturyApiVersion")
//
//    compileOnly(project(":common"))
//}
//
//val notNeoTask: Spec<Task> = Spec { !it.name.startsWith("neo") }
//
//tasks {
//    withType<KotlinCompile>().matching(notNeoTask).configureEach {
//        source(project(":common").sourceSets.main.get().allSource)
//    }
//
//    withType<Javadoc>().matching(notNeoTask).configureEach {
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
//}
//
//publishing {
//    publications {
//        register("mavenJava", MavenPublication::class) {
//            artifactId = base.archivesName.get()
//            artifact(tasks.jar)
//        }
//    }
//
//    repositories {
//        maven("file://${System.getenv("local_maven")}")
//    }
//}

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
    modImplementation("dev.architectury:architectury-neoforge:$architecturyApiVersion")
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
