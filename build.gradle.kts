import net.minecraftforge.gradle.userdev.UserDevExtension
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.spongepowered.asm.gradle.plugins.MixinExtension

buildscript {
    dependencies {
        classpath("org.spongepowered:mixingradle:0.7-SNAPSHOT")
    }
}

plugins {
    java
    eclipse
    idea
    `maven-publish`
    id("net.minecraftforge.gradle") version "6.+"
    kotlin("jvm") version "1.9.0-Beta"
}

apply(plugin = "org.spongepowered.mixin")

group = "team.zeromods"
version = "1.0-SNAPSHOT"

evaluationDependsOnChildren()

val mc_version: String by project
val forge_version: String by project

val coroutines_version: String by project
val serialization_version: String by project

val shadow: Configuration by configurations.creating {
    exclude("org.jetbrains", "annotations")
}
//val apiImplementation: Configuration by configurations.creating {
//    extendsFrom(implementation)
//}
//val apiRuntimeOnly: Configuration by configurations.creating {
//    extendsFrom(runtimeOnly)
//}

sourceSets {
    create("api") {
        resources.srcDirs()
    }
    named("main") {
        compileClasspath += sourceSets["api"].output
        runtimeClasspath += sourceSets["api"].output
        resources.srcDir("src/generated/resources")
    }
}

val main = sourceSets["main"]
val api = sourceSets["api"]

jarJar.enable()

configurations {
    apiElements {
        artifacts.clear()
    }
    runtimeElements {
        setExtendsFrom(emptySet())

        artifacts.clear()
        outgoing.artifact(tasks.jarJar)
    }
    minecraftLibrary {
        extendsFrom(shadow)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

configure<UserDevExtension> {
    mappings("official", "$mc_version")

    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    runs {
        create("client") {
            workingDirectory (project.file("run"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", "AncientMagic")
            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${buildDir}/createSrgToMcp/output.srg")
            arg("-mixin.config=ancient.mixins.json")

            mods {
                create("ancientmagic") {
                    sources(the<JavaPluginExtension>().sourceSets.getByName("main"), the<JavaPluginExtension>().sourceSets.getByName("api"))
                }
            }
        }

        create("server") {
            workingDirectory (project.file("run"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", "AncientMagic")
            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${buildDir}/createSrgToMcp/output.srg")
            arg("-mixin.config=ancient.mixins.json")

            mods {
                create("ancientmagic") {
                    sources(the<JavaPluginExtension>().sourceSets.getByName("main"), the<JavaPluginExtension>().sourceSets.getByName("api"))
                }
            }
        }

        create("gameTestServer") {
            workingDirectory (project.file("run"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", "AncientMagic")
            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${buildDir}/createSrgToMcp/output.srg")
            arg("-mixin.config=ancient.mixins.json")

            mods {
                create("ancientmagic") {
                    sources(the<JavaPluginExtension>().sourceSets.getByName("main"), the<JavaPluginExtension>().sourceSets.getByName("api"))
                }
            }
        }

        create("data") {
            workingDirectory (project.file("run"))

            property("forge.logging.markers", "REGISTRIES")

            property("forge.logging.console.level", "debug")

            args("--mod", "ancientmagic", "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))

            mods {
                create("ancientmagic") {
                    sources(the<JavaPluginExtension>().sourceSets.getByName("main"), the<JavaPluginExtension>().sourceSets.getByName("api"))
                }
            }
        }
    }
}

configure<MixinExtension> {
    add(main, "ancient.refmap.json")
}

repositories {
    maven("https://nexus.twelveiterations.com/repository/maven-public/")
    maven("https://maven.theillusivec4.top/")

    mavenCentral()
}

dependencies {
    minecraft("net.minecraftforge:forge:$mc_version-+")

//    implementation fg.deobf("net.blay09.mods:waystones-forge:13.1.0+1.19.4")
//    implementation fg.deobf("net.blay09.mods:waystones-common:13.1.0+1.19.4")
//
//    implementation fg.deobf("net.blay09.mods:balm-forge:6.0.2+1.19.4")
//    implementation fg.deobf("net.blay09.mods:balm-common:6.0.2+1.19.4")
//
//    implementation fg.deobf("top.theillusivec4.curios:curios-forge:1.19.4-5.1.5.3")
//    implementation fg.deobf("top.theillusivec4.curios:curios-forge:1.19.4-5.1.5.3:api")

    shadow("org.jetbrains.kotlin:kotlin-reflect:${kotlin.coreLibrariesVersion}")
    shadow("org.jetbrains.kotlin:kotlin-stdlib:${kotlin.coreLibrariesVersion}")
    shadow("org.jetbrains.kotlin:kotlin-stdlib-common:${kotlin.coreLibrariesVersion}")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutines_version}")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${coroutines_version}")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${coroutines_version}")
    shadow("org.jetbrains.kotlinx:kotlinx-serialization-core:${serialization_version}")
    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:${serialization_version}")

    implementation("org.jetbrains:annotations")
    implementation(kotlin("stdlib"))

    implementation("org.projectlombok:lombok:1.18.+") // mutable api version

    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

tasks {
    withType<Jar> {
        from(main.output)
        from(api.output)
        manifest {
            attributes(
                mapOf(
                    "Specification-Title" to "ancientmagic",
                    "Specification-Vendor" to "AlgorithmLX",
                    "Specification-Version" to "1",
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to version,
                    "Implementation-Timestamp" to ZonedDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")),
                    "MixinConfigs" to "ancient.mixins.json"
                )
            )
        }
        finalizedBy("reobfJar")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
//    register("sourcesJar") {
//        duplicatesStrategy.set(DuplicatesStrategy.FAIL)
//        archiveClassifier.set("sources")
//        from(the<JavaPluginExtension>().sourceSets.getByName("main").allJava)
//        from(the<JavaPluginExtension>().sourceSets.getByName("api").allJava)
//    }
//
//    register("apiJar") {
//        duplicatesStrategy(DuplicatesStrategy.FAIL)
//        archiveClassifier.set("api")
//        from(the<JavaPluginExtension>().sourceSets.getByName("api").output)
//        afterEvaluate {
//            finalizedBy(tasks.getByName("reobfApiJar"))
//        }
//
//        from(the<JavaPluginExtension>().sourceSets.getByName("api").allJava)
//    }
//
//    register("deobfJar") {
//        duplicatesStrategy(DuplicatesStrategy.FAIL)
//        archiveClassifier.set("deobf")
//        from(the<JavaPluginExtension>().sourceSets.getByName("main").output)
//        from(the<JavaPluginExtension>().sourceSets.getByName("api").output)
//    }
//    register("reobf") {
//        dependsOn("reobfJar")
//        dependsOn("reobfApiJar")
//    }
}
kotlin {
    jvmToolchain(17)
}