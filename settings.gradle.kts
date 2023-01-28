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

include(":presentation:app:handheld")
include(":presentation:app:wearable")
include(":presentation:di:common")
include(":presentation:di:mobile")
include(":presentation:di:wear")
include(":presentation:shared")

include(":data:common")
include(":data:communication")
include(":data:handheld")
include(":data:wearable")

include(":domain:common")
include(":domain:communication")
include(":domain:handheld")
include(":domain:wearable")
