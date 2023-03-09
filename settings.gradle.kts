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

include(":communication")
include(":communication:connection")
include(":communication:failures")
include(":communication:impl:data-layer")
include(":communication:impl:data-layer:service")
include(":communication:models:rpc")

include(":data:common")
include(":data:handheld")
include(":data:wearable")

include(":di:common")
include(":di:handheld")
include(":di:wearable")

include(":domain:common")
include(":domain:common:models")
include(":domain:handheld")
include(":domain:wearable")

include(":presentation:shared")

include(":utils:resource")
