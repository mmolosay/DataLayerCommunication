plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")// TODO: remove
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":communication:models"))
    implementation(project(":domain:common:models")) // TODO: remove
    implementation(project(":utils:resource"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")// TODO: remove
    implementation("javax.inject:javax.inject:1") // TODO: remove separately
}