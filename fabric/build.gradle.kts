plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val modName: String by rootProject

architectury {
    platformSetupLoomIde()
    fabric()
}

base {
    archivesName.set("${archivesName.get()}-fabric")
}

val common: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    named("developmentFabric") { extendsFrom(common) }
}

dependencies {
    val fabricLoaderVersion: String by rootProject
    val fabricVersion: String by rootProject
    val architecturyApiVersion: String by rootProject
    val clothVersion: String by rootProject
    val klfVersion: String by rootProject

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion") { include(this) }
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion") { include(this) }
    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion") { include(this) }
    modImplementation("net.fabricmc:fabric-language-kotlin:$klfVersion") { include(this) }

    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothVersion") {
        exclude(group = "net.fabricmc.fabric-api")
        include(this)
    }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(path = ":common", configuration = "transformProductionFabric"))
}

tasks {
    processResources {
        val modId: String by rootProject
        from(sourceSets.main.get().resources)
        filesMatching(listOf("$modId.mixins.json", "$modId.fabric.mixins.json")) {
            expand("team._0mods.$modId.mixin" to "team._0mods.$modId.fabric.mixin")
        }
    }

    shadowJar {
        configurations = listOf(shadowBundle)
        archiveClassifier = "dev-shadow"
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenFabric") {
            artifactId = "${base.archivesName.get()}-fabric"
            from(components["kotlin"])
        }
    }

    repositories {
        val mk = System.getenv("MAVEN_KEY")
        val mp = System.getenv("MAVEN_PASS")
        val releaseType: String by rootProject
        val artefact = if (releaseType.isEmpty()) "releases" else "snapshots"

        logger.info("MAVEN_KEY: $mk")
        logger.info("MAVEN_PASS: $mp")

        if (mk != null && mp != null) {
            maven("https://maven.0mods.team/$artefact/") {
                credentials {
                    username = mk
                    password = mp
                }
            }
        }
    }
}
