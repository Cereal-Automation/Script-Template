apply(from = "proguard.gradle")

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":cereal-api"))
    testImplementation(kotlin("test"))
    testImplementation(project(":cereal-test-utils"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("io.mockk:mockk:1.13.2")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjvm-default=enable")
        }
        sourceCompatibility = "11"
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjvm-default=enable")
        }
        sourceCompatibility = "11"
    }

    jar {
        archiveFileName.set("script-release.jar")
    }
}
