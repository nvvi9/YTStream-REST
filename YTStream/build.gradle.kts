import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

application {
    mainClassName = "com.nvvi9.YTStream"
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