import io.gitlab.arturbosch.detekt.Detekt

plugins {
    kotlin("multiplatform") version "1.8.20"
    id("io.gitlab.arturbosch.detekt") version("1.23.0")
    id("io.ktor.plugin") version "2.3.1"
    id("io.kotest.multiplatform") version "5.6.2"
    application
}

group = "dev.mmauro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm("backend") {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js("frontend", IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    val kotestVersion = "5.6.2"
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
                implementation("io.kotest:kotest-framework-engine:$kotestVersion")
                implementation("io.kotest:kotest-framework-datatest:$kotestVersion")
            }
        }
        val backendMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:2.0.2")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
            }
        }
        val backendTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
            }
        }
        val frontendMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.346")
            }
        }
        val frontendTest by getting {
            dependencies {
                implementation("io.kotest:kotest-framework-engine:$kotestVersion")
            }
        }
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.0")
}

tasks.named<Test>("backendTest") {
    useJUnitPlatform()
}

detekt {
    toolVersion = "1.23.0"
    config.setFrom(file("detekt.yml"))
    buildUponDefaultConfig = true
}

application {
    mainClass.set("dev.mmauro.privateMediaGallery.server.ServerKt")
}

ktor {
    fatJar {
        archiveFileName.set("private-media-gallery.jar")
    }
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        md.required.set(true)
    }
}

tasks.register("detektAll") {
    group = "verification"
    dependsOn += "detektBackendMain"
    dependsOn += "detektBackendTest"
    dependsOn += "detektFrontendMain"
    dependsOn += "detektFrontendTest"
    dependsOn += "detektMetadataCommonMain"
}

tasks.buildFatJar {
    dependsOn += "detektAll"
}

tasks.named<Copy>("backendProcessResources") {
    from(tasks.named("frontendBrowserDistribution"))
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("backendJar"))
    classpath(tasks.named<Jar>("backendJar"))
}

