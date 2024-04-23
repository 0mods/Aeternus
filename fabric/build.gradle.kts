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


dependencies {
    val fabricLoaderVersion: String by rootProject
    val fabricVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    val clothVersion: String by rootProject
    val klfVersion: String by rootProject

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion")

    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothVersion") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    modImplementation("net.fabricmc:fabric-language-kotlin:$klfVersion")
    include("net.fabricmc:fabric-language-kotlin:$klfVersion")

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

