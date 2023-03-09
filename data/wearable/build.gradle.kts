plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.github.mmolosay.datalayercommunication.data.wearable"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":communication"))
    implementation(project(":communication:connection"))
    implementation(project(":communication:failures"))
    implementation(project(":communication:models:rpc"))
    implementation(project(":domain:common"))
    implementation(project(":domain:common:models"))
    implementation(project(":domain:wearable"))
    implementation(project(":utils:resource"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    testImplementation("io.kotest:kotest-assertions-core:5.1.0")
    testImplementation("junit:junit:4.13.2")
}