plugins {
    `java-library`
    eclipse
    `maven-publish`
    kotlin("jvm")
    id("net.neoforged.gradle.userdev") version "[7.0,8.0)"
}

val parchmentMCVersion: String by project
val parchmentVersion: String by project
val minecraftVersion: String by project
val neoAtsEnabled: String by project
val modName: String by project
val modAuthor: String by project
val modId: String by project

val baseArchiveName = "${modName}-neo-${minecraftVersion}"

base {
    archivesName.set(baseArchiveName)
}

if (neoAtsEnabled.toBoolean())
    minecraft.accessTransformers.file(file("src/main/resources/META-INF/accesstransformer.cfg"))

subsystems.parchment {
    minecraftVersion(parchmentMCVersion)
    mappingsVersion(parchmentVersion)
}

runs {
    configureEach { modSource(project.sourceSets.main.get()) }

    create("client") { systemProperty("neoforge.enableGameTestNamespace", modId) }

    create("server") {
        systemProperty("neoforge.enabledGameTestNamespaces", modId)
        programArgument("--nogui")
    }

    create("gameTestServer") { systemProperty("neoforge.enabledGameTestNamespaces", modId) }

    create("data") {
        programArguments(
                "--mod", modId,
                "--all",
                "--output", file("src/generated/resources").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
        )
    }
}

sourceSets.main.get().resources.srcDir("src/generated/resources")

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/")
}

dependencies {
    val kffVersion: String by project
    val neoVersion: String by project

    implementation("net.neoforged:neoforge:${neoVersion}")
    implementation("thedarkcolour:kotlinforforge:$kffVersion")
    compileOnly(project(":Common"))
}

tasks {
    withType<JavaCompile> {
        source(project(":Common").sourceSets.main.get().allSource)
    }

    processResources {
        from(project(":Common").sourceSets.main.get().resources)
    }

    jar {
        finalizedBy("reobfJar")
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = baseArchiveName
            artifact(tasks.jar)
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}
