plugins {
    val agpV = "7.3.1"
    val kotlinV = "1.8.10"

    id("com.android.application") version agpV apply false
    id("com.android.library") version agpV apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jetbrains.kotlin.android") version kotlinV apply false
    id("org.jetbrains.kotlin.jvm") version kotlinV apply false
    id("org.jetbrains.kotlin.plugin.serialization") version kotlinV apply false
}
