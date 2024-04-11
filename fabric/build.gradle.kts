import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    `maven-publish`
    id("fabric-loom") version("1.4.+")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val minecraftVersion: String by project
val modName: String by project
val modId: String by project
val loomVersion: String by project

base {
    archivesName = "${modName}-fabric-${minecraftVersion}"
}

dependencies {
    val fabricVersion: String by project
    val fabricLoaderVersion: String by project
    val clothVersion: String by project
    val klfVersion: String by project
    val parchmentMCVersion: String by project
    val parchmentVersion: String by project

    minecraft("com.mojang:minecraft:${minecraftVersion}")
    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${parchmentMCVersion}:${parchmentVersion}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothVersion") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    modImplementation("net.fabricmc:fabric-language-kotlin:$klfVersion")
    include("net.fabricmc:fabric-language-kotlin:$klfVersion")
    compileOnly(project(":common"))
}

loom {
    val fileAW = project(":common").file("src/main/resources/${modId}.accesswidener")
    if (fileAW.exists()) {
        println("Common AccessWidener is founded")
        accessWidenerPath.set(fileAW)
    }

    @Suppress("UnstableApiUsage")
    mixin { defaultRefmapName.set("${modId}.refmap.json") }

    runs {
        named("client") {
            client()
            configName = "Fabric: Client (Aeternus)"
            ideConfigGenerated(true)
            runDir("run")
            vmArg("-XX:+AllowEnhancedClassRedefinition")
        }
        named("server") {
            server()
            configName = "Fabric: Server (Aeternus)"
            ideConfigGenerated(true)
            runDir("run")
            vmArg("-XX:+AllowEnhancedClassRedefinition")
        }
    }
}

tasks {
    withType<KotlinCompile> {
        source(project(":common").sourceSets.main.get().allSource)
    }

    javadoc {
        source(project(":common").sourceSets.main.get().allJava)
    }

    named("sourcesJar", Jar::class) {
        from(project(":common").sourceSets.main.get().allSource)
    }

    processResources {
        from(project(":common").sourceSets.main.get().resources)
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}
