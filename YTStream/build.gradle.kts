import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
}

application {
    mainClassName = "com.nvvi9.YTStream"
}

val coroutinesVersion = "1.3.8"
val rxJavaVersion = "3.0.5"
val retrofitVersion = "2.9.0"
val jacksonVersion = "2.11.2"
val junitVersion = "4.13"

dependencies {
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:$coroutinesVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

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
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}