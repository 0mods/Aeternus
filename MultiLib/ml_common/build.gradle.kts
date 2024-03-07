plugins {
    java
    `maven-publish`
    kotlin("jvm")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val minecraftVersion: String by project
val modName: String = "MultiLibAPI"
val modId: String = "multilib"

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
    compileOnly("com.electronwill.night-config:core:3.6.7")
    compileOnly("com.electronwill.night-config:toml:3.6.7")
    compileOnly("com.electronwill.night-config:yaml:3.6.7")
    compileOnly("com.electronwill.night-config:json:3.6.7")
    compileOnly("com.electronwill.night-config:hocon:3.6.7")
//    implementation("com.google.code.findbugs:jsr305:3.0.1")
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
