plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":communication:failures"))
    implementation(project(":communication:models"))
    implementation(project(":communication:models:rpc"))
    implementation(project(":utils:resource"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}