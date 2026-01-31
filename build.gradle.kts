plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.cereal-automation.com/releases")
        }
    }

    apply(plugin = "com.gradleup.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("release.jar")

        dependencies {
            // Kotlin is included in the Cereal client by default so leave it out to make the script binary smaller and to
            // prevent conflicts with coroutines, which is also used in the Scripts' interface.
            exclude("DebugProbesKt.bin", "META-INF/**", "*.jpg", "kotlin/**")
        }
    }

    tasks.register("scriptJar", proguard.gradle.ProGuardTask::class.java) {
        description = "Build script jar with obfuscation"
        dependsOn("shadowJar")

        val artifactName = "release.jar"
        val buildDir = layout.buildDirectory.get()
        val cerealScriptFolder = "$buildDir/cereal"

        injars("$buildDir/libs/$artifactName")
        outjars("$cerealScriptFolder/$artifactName")

        // Mapping for debugging
        printseeds("$cerealScriptFolder/seeds.txt")
        printmapping("$cerealScriptFolder/mapping.txt")

        // Dependencies
        libraryjars(sourceSets.main.get().compileClasspath)

        configuration(
            files(
                "${rootDir.absolutePath}/proguard-rules/script.pro",
                "${rootDir.absolutePath}/proguard-rules/coroutines.pro",
            ),
        )
    }
}

buildscript {
    dependencies {
        classpath(libs.proguard.gradle)
    }
}

dependencies {
    compileOnly(libs.cereal.sdk) {
        artifact {
            classifier = "all"
        }
    }
    implementation(libs.cereal.licensing)

    testImplementation(kotlin("test"))
    testImplementation(libs.cereal.test.utils)
    testImplementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.mockk)
}

tasks {
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}
