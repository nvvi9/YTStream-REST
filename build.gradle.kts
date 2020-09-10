plugins {
    kotlin("jvm") version "1.4.10" apply false
}

val coroutinesVersion = "1.3.8"
val jacksonVersion = "2.11.2"
val rxJavaVersion = "3.0.5"
val retrofitVersion = "2.9.0"
val junitVersion = "4.13"
val vertxVersion = "4.0.0.Beta1"
val koinVersion = "2.1.6"

subprojects {
    apply(plugin = "application")
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "com.nvvi9"
    version = "0.1.1"

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        // Kotlin
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib")

        // Coroutines
        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

        // Jackson
        "implementation"("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    }
}

project(":YTStream") {
    dependencies {
        // Coroutines
        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:$coroutinesVersion")

        // Retrofit
        "implementation"("com.squareup.retrofit2:retrofit:$retrofitVersion")

        // JUnit
        "testImplementation"("junit:junit:$junitVersion")

        // RxJava test
        "testImplementation"("io.reactivex.rxjava3:rxjava:$rxJavaVersion")
    }
}

project(":YTStream-REST") {
    dependencies {
        // Vert.x
        "implementation"("io.vertx:vertx-core:$vertxVersion")
        "implementation"("io.vertx:vertx-lang-kotlin:$vertxVersion")
        "implementation"("io.vertx:vertx-web:$vertxVersion")
        "implementation"("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")

        // Jackson
        "implementation"("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

        // Koin
        "implementation"("org.koin:koin-core:$koinVersion")

        // YTStream
        "implementation"(project(":YTStream"))
    }
}