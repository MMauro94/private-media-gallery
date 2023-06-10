import io.gitlab.arturbosch.detekt.Detekt

plugins {
    kotlin("multiplatform") version "1.8.20"
    id("io.gitlab.arturbosch.detekt") version("1.23.0")
    id("io.ktor.plugin") version "2.3.1"
    application
}

group = "dev.mmauro"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:2.0.2")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.346")
            }
        }
        val jsTest by getting
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.0")
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
    dependsOn += "detektJvmMain"
    dependsOn += "detektJvmTest"
    dependsOn += "detektJsMain"
    dependsOn += "detektJsTest"
    dependsOn += "detektMetadataCommonMain"
}

tasks.buildFatJar {
    dependsOn += "detektAll"
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}

