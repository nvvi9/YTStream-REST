import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

application {
    mainClassName = "com.nvvi9.YTStream"
}

group = "com.nvvi9"
version = "0.1.1"

repositories {
    mavenCentral()
    jcenter()
}

val coroutinesVersion = "1.3.8"
val rxJavaVersion = "3.0.5"
val retrofitVersion = "2.9.0"
val jacksonVersion = "2.11.2"
val junitVersion = "4.13"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:$coroutinesVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // JUnit
    testImplementation("junit:junit:$junitVersion")

    // RxJava test
    testImplementation("io.reactivex.rxjava3:rxjava:$rxJavaVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
        }
    }

    withType(GradleBuild::class.java) {
        dependsOn(shadowJar)
    }

    named<ShadowJar>("shadowJar") {
        archiveBaseName.set(project.name)
        archiveClassifier.set("fat")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}