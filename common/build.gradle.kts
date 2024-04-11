plugins {
    java
    `maven-publish`
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val minecraftVersion: String by project
val modName: String by project
val modId: String by project

base {
    archivesName.set("${modName}-common-${minecraftVersion}")
}

minecraft {
    version(minecraftVersion)
    if (file("src/main/resources/${modId}.accesswidener").exists()) {
        println("Common AccessWidener is founded")
        accessWideners("src/main/resources/${modId}.accesswidener")
    }
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")
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
