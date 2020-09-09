subprojects {
    apply(plugin = "application")
    apply(plugin = "java")

    group = "com.nvvi9"
    version = "0.1.1"

    repositories {
        mavenCentral()
        jcenter()
    }

    val coroutinesVersion = "1.3.8"
    val jacksonVersion = "2.11.2"

    dependencies {
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib")
        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        "implementation"("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    }
}