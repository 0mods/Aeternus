import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    kotlin("jvm")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val minecraftVersion: String by project
val modName: String by project
val modId: String by project

base {
    archivesName = "${modName}-common-${minecraftVersion}"
}

minecraft {
    version(minecraftVersion)
    if (file("src/main/resources/${modId}.accesswidener").exists())
        accessWideners("src/main/resources/${modId}.accesswidener")
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")
    compileOnly(project(":MultiLib:common"))
//    implementation("com.google.code.findbugs:jsr305:3.0.1")
}

tasks {
    withType<KotlinCompile> { source(project(":MultiLib:common").sourceSets.main.get().allSource) }

    javadoc { source(project(":MultiLib:common").sourceSets.main.get().allJava) }

    named("sourcesJar", Jar::class) { from(project(":MultiLib:common").sourceSets.main.get().allSource) }

    processResources { from(project(":MultiLib:common").sourceSets.main.get().resources) }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = base.archivesName.get()
            from(components["kotlin"])
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}
