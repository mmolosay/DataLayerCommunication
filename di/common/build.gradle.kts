plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = 33
    namespace = "io.github.mmolosay.datalayercommunication.di"

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

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":communication"))
    implementation(project(":communication:impl:data-layer"))
    implementation(project(":communication:impl:data-layer:service"))
    implementation(project(":communication:models:rpc"))
    implementation(project(":data:common"))
    implementation(project(":domain:common"))
    implementation(project(":domain:common:models"))
    implementation(project(":utils:resource"))

    implementation("com.google.android.gms:play-services-wearable:18.0.0")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}