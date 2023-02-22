plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":communication"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("javax.inject:javax.inject:1")
}