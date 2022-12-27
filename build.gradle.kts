apply(from = "packaging.gradle")
apply(from = "proguard.gradle")

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    maven {
        url = uri("https://repo.repsy.io/mvn/cereal/release")
    }
    mavenCentral()
}

dependencies {
    implementation("com.cereal-automation:cereal-api:0.5.0")
    implementation("com.cereal-automation:cereal-licensing:0.5.0")

    testImplementation(kotlin("test"))
    testImplementation("com.cereal-automation:cereal-test-utils:0.5.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("io.mockk:mockk:1.13.2")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjvm-default=all")
        }
        sourceCompatibility = "11"
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjvm-default=all")
        }
        sourceCompatibility = "11"
    }

    jar {
        archiveFileName.set("release.jar")
    }
}
