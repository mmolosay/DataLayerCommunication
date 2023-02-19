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

include(":app:handheld")
include(":app:wearable")
include(":di:common")
include(":di:mobile")
include(":di:wear")
include(":presentation:shared")

include(":communication")
include(":communication:impl:data-layer")

include(":data:common")
include(":data:handheld")
include(":data:wearable")

include(":domain:common")
include(":domain:handheld")
include(":domain:wearable")
