import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.google.protobuf.gradle.*

plugins {
    id("com.github.johnrengelman.shadow") version "5.0.0"
    id("com.google.protobuf") version "0.8.13"
    java
    idea
}

application {
    mainClassName = "com.nvvi9.grpc.VideoDataServer"
}

java {
    sourceCompatibility = JavaVersion.toVersion("1.8")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveClassifier.set("shadow")
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
        }
        id("rxgrpc") {
            artifact = "com.salesforce.servicelibs:rxgrpc:1.0.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
                id("rxgrpc")
            }
        }
    }
}

sourceSets.main.get().allSource.srcDir("generated/source/proto/main/grpc")
sourceSets.main.get().allSource.srcDir("generated/source/proto/main/rxgrpc")
sourceSets.main.get().allSource.srcDir("generated/source/proto/main/kotlin")
sourceSets.main.get().allSource.srcDir("generated/source/proto/main/java")

idea {
    module {
        generatedSourceDirs.plusAssign(file("build/generated/source/proto/main/grpc"))
        generatedSourceDirs.plusAssign(file("build/generated/source/proto/main/rxgrpc"))
        generatedSourceDirs.plusAssign(file("build/generated/source/proto/main/kotlin"))
        generatedSourceDirs.plusAssign(file("build/generated/source/proto/main/java"))
    }
}
