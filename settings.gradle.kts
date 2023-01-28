pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

// https://docs.gradle.org/7.4/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":app:handeheld")
include(":app:wearable")

include(":data:common")
include(":data:communication")
include(":data:mobile")
include(":data:wear")

include(":domain:common")
include(":domain:communication")
include(":domain:mobile")
include(":domain:wear")

include(":di:common")
include(":di:mobile")
include(":di:wear")
