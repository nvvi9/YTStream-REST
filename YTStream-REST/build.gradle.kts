import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

application {
    mainClassName = "io.vertx.core.Launcher"
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