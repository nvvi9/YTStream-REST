import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

application {
    mainClassName = "io.vertx.core.Launcher"
}

group = "com.nvvi9"
version = "0.1.1"

repositories {
    mavenCentral()
    jcenter()
}

val coroutinesVersion = "1.3.8"
val vertxVersion = "4.0.0.Beta1"
val shadowVersion = "5.0.0"
val jacksonVersion = "2.11.2"
val koinVersion = "2.1.6"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Vert.x
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // Koin
    implementation("org.koin:koin-core:$koinVersion")

    // YTStream
    implementation(project(":YTStream"))
}

val mainVerticleName = "MainVerticle"
val watchForChange = "src/**/*.*"
val doOnChange = "$projectDir/gradlew classes"

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("ytstream-rest")
        archiveClassifier.set("")
        manifest {
            attributes("Main-Verticle" to mainVerticleName)
        }
        mergeServiceFiles {
            include("META-INF/services/io.vertx.core.spi.VerticleFactory")
        }
    }

    getByName<JavaExec>("run") {
        args = listOf("run", mainVerticleName, "--launcher-class=${application.mainClassName}", "--redeploy=$watchForChange", "--on-redeploy=$doOnChange")
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    register("stage") {
        dependsOn("shadowJar")
    }
}