import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    eclipse
    `maven-publish`
    kotlin("jvm")
    id("net.neoforged.gradle.userdev") version "[7.0,8.0)"
}

jarJar.enable()

val parchmentMCVersion: String by project
val parchmentVersion: String by project
val minecraftVersion: String by project
val modName: String by project
val modAuthor: String by project
val modId: String by project

base {
    archivesName = "${modName}-neo-${minecraftVersion}"
}

val transformerFile = file("src/main/resources/META-INF/accesstransformer.cfg")
if (transformerFile.exists())
    minecraft.accessTransformers.file(transformerFile)

subsystems.parchment {
    minecraftVersion(parchmentMCVersion)
    mappingsVersion(parchmentVersion)
}

runs {
    configureEach { modSource(project.sourceSets.main.get()) }

    create("client") { 
        systemProperty("neoforge.enableGameTestNamespace", modId) 
        jvmArguments("-XX:+AllowEnhancedClassRedefinition")
    }

    create("server") {
        systemProperty("neoforge.enabledGameTestNamespaces", modId)
        programArgument("--nogui")
        jvmArguments("-XX:+AllowEnhancedClassRedefinition")
    }

    create("gameTestServer") { 
        systemProperty("neoforge.enabledGameTestNamespaces", modId)
        jvmArguments("-XX:+AllowEnhancedClassRedefinition")
    }

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

dependencies {
    val kffVersion: String by project
    val neoVersion: String by project

    implementation("net.neoforged:neoforge:$neoVersion")
    implementation("thedarkcolour:kotlinforforge-neoforge:$kffVersion")
    compileOnly(project(":common"))
    compileOnly(project(":multilib_neoforge"))
    jarJar(project(":multilib_neoforge"))
}

val notNeoTask: Spec<Task> = Spec { it: Task -> !it.name.startsWith("neo") }

tasks {
    withType<KotlinCompile>().matching(notNeoTask).configureEach {
        source(project(":common").sourceSets.main.get().allSource)
    }

    withType<Javadoc>().matching(notNeoTask).configureEach {
        source(project(":common").sourceSets.main.get().allJava)
    }

    named("sourcesJar", Jar::class) {
        from(project(":common").sourceSets.main.get().allSource)
    }

    processResources {
        from(project(":common").sourceSets.main.get().resources)
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = base.archivesName.get()
            artifact(tasks.jar)
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}
