import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    `maven-publish`
}

group = "com.nvvi9"
version = "0.1.0"

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
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

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

    withType<GenerateMavenPom> {
        destination = file("$buildDir/libs/${project.name}-${project.version}.pom")
    }

    withType<Jar> {
        archiveBaseName.set(project.name)
        manifest {
            attributes(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version,
                    "Class-Path" to configurations.compileClasspath.get().joinToString(" ") { it.name }
            )
        }
    }

    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
        moduleName = rootProject.name
    }

    publishToMavenLocal {
        dependsOn(build)
    }
}

val shadowJar: ShadowJar by tasks
shadowJar.apply {
    archiveBaseName.set(project.name)
    archiveClassifier.set("")
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
    dependsOn(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val bintrayArtifactId = project.name
val bintrayArtifactGroupID = project.group.toString()
val bintrayArtifactVersion = project.version.toString()

val bintrayRepository = "YTStream"

val issueUrl = "https://github.com/nvvi9/YTStream/issues"
val pageUrl = "https://github.com/nvvi9/YTStream"
val gitUrl = "https://github.com/nvvi9/YTStream.git"
val githubTag = "v0.1.0"

val bintrayDesc = "Library for extracting YouTube video streaming URLs."

val devId = "nvvi9"

publishing {
    publications {
        create<MavenPublication>(bintrayRepository) {
            groupId = bintrayArtifactGroupID
            artifactId = bintrayArtifactId
            version = bintrayArtifactVersion
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)

            pom.withXml {
                asNode().apply {
                    appendNode("name", rootProject.name)
                    appendNode("description", bintrayDesc)
                    appendNode("url", pageUrl)
                    appendNode("developers").appendNode("developer").appendNode("id", devId)

                }
            }
        }
    }
}

val bintrayUser: String by project
val bintrayApiKey: String by project

bintray {
    if (project.hasProperty("bintrayUser")) {
        user = bintrayUser
    }
    if (project.hasProperty("bintrayApiKey")) {
        key = bintrayApiKey
    }
    publish = false

    setPublications("YTStream")

    pkg.apply {
        repo = bintrayRepository
        name = bintrayArtifactGroupID
        vcsUrl = gitUrl
        websiteUrl = pageUrl
        issueTrackerUrl = issueUrl
        description = bintrayDesc
        setLabels("youtube", "youtube-dl", "stream", "video", "audio", "jvm", "kotlin")
        setLicenses("Apache-2.0")

        version.apply {
            name = bintrayArtifactVersion
            desc = pageUrl
            vcsTag = githubTag
        }
    }
}
