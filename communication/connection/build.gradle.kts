plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":communication")) // TODO: extract models and depend on them

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}