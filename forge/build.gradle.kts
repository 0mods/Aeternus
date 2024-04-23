import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val modId: String by rootProject

loom {
    forge {
        convertAccessWideners = true
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
        mixinConfigs("$modId.mixins.json")
    }
}

architectury {
    platformSetupLoomIde()
    forge()
}

dependencies {
    val forgeVersion: String by rootProject
    val kffVersion: String by rootProject
    val minecraftVersion: String by rootProject
    val architecturyApiVersion: String by rootProject

    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")

    modImplementation("dev.architectury:architectury-fabric:$architecturyApiVersion")
    implementation("thedarkcolour:kotlinforforge:$kffVersion")

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
