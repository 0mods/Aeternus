plugins {
    java
    `maven-publish`
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val minecraftVersion: String by project
val modName: String by project
val modId: String by project

val enabledPlatforms: String by project

architectury {
    common(enabledPlatforms.split(","))
}

dependencies {
    val architecturyApiVersion: String by rootProject
    modApi("dev.architectury:architectury:$architecturyApiVersion")

    compileOnly("org.spongepowered:mixin:0.8.5")
    implementation("com.google.code.findbugs:jsr305:3.0.1")
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
