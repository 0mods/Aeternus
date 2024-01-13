val minecraftVersion: String by project
val modName: String by project
val modId: String by project
val loomVersion: String by project

plugins {
    idea
    `maven-publish`
    id("fabric-loom") version("1.4.+")
    kotlin("jvm")
}

val baseArchiveName = "${modName}-fabric-${minecraftVersion}"

base {
    archivesName.set(baseArchiveName)
}

repositories {
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
}

loom {
    val file = project(":Common").file("src/main/resources/${modId}.accesswidener")

    if (file.exists()) accessWidenerPath.set(file)

    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
            vmArg("-XX:+AllowEnhancedClassRedefinition")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run")
            vmArg("-XX:+AllowEnhancedClassRedefinition")
        }
    }
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
    implementation(project(":Common"))
}

tasks.processResources {
    from(project(":Common").sourceSets.main.get().resources)
}

tasks.withType<JavaCompile> {
    source(project(":Common").sourceSets.main.get().allSource)
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${modName}" }
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = baseArchiveName
            from(components["java"])
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}
