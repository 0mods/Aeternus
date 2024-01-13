plugins {
    java
    eclipse
    id("net.minecraftforge.gradle") version "[6.0,6.2)"
    `maven-publish`
    kotlin("jvm")
    id("org.spongepowered.mixin") version "0.7.38"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
}

apply(plugin = "org.spongepowered.mixin")

val minecraftVersion: String by project
val modName: String by project
val modAuthor: String by project
val modId: String by project

val baseArchiveName = "${modName}-forge-${minecraftVersion}"

base {
    archivesName.set(baseArchiveName)
}

mixin {
    add(sourceSets.main.get(), "${modId}.refmap.json")

    config("${modId}.mixins.json")
    config("${modId}.forge.mixins.json")
}

minecraft {
    val parchmentMCVersion: String by project
    val parchmentVersion: String by project
    mappings("parchment", "${parchmentMCVersion}-$parchmentVersion")

    if (file("src/main/resources/META-INF/accesstransformer.cfg").exists()) {
        // This location is hardcoded in Forge and can not be changed.
        // https://github.com/MinecraftForge/MinecraftForge/blob/be1698bb1554f9c8fa2f58e32b9ab70bc4385e60/fmlloader/src/main/java/net/minecraftforge/fml/loading/moddiscovery/ModFile.java#L123
        accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))
        project.logger.debug("Forge Access Transformers are enabled for this project.")
    }

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            taskName("Client")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
            jvmArgs("-XX:+AllowEnhancedClassRedefinition")
        }

        create("server") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            taskName("Server")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
            jvmArgs("-XX:+AllowEnhancedClassRedefinition")
        }

        create("data") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("--mod", modId, "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))
            taskName("Data")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
        }
    }
}

sourceSets.main.get().resources.srcDir("src/generated/resources")

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/")
}

dependencies {
    val kffVersion: String by project
    val forgeVersion: String by project

    minecraft("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
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
