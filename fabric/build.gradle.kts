import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    `maven-publish`
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val modName: String by rootProject

architectury {
    platformSetupLoomIde()
    fabric()
}

base {
    val modVersion: String by rootProject
    val modName: String by rootProject
    val minecraftVersion: String by rootProject

    archivesName.set("$modName-fabric-${modVersion}_$minecraftVersion")
}

dependencies {
    val fabricLoaderVersion: String by rootProject
    val fabricVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    val clothVersion: String by rootProject
    val klfVersion: String by rootProject

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion")
    modImplementation("net.fabricmc:fabric-language-kotlin:$klfVersion")

    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothVersion") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    include("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    include("dev.architectury:architectury-fabric:$architecturyApiVersion")
    include("net.fabricmc:fabric-language-kotlin:$klfVersion")
    include("me.shedaniel.cloth:cloth-config-fabric:$clothVersion")

    compileOnly(project(":common"))
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

