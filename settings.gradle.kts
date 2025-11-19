@file:Suppress("UnstableApiUsage")

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "GDS_Android_Logging"

include(
    ":modules:logging-api",
    ":modules:logging-impl",
    ":modules:logging-testdouble"
)

includeBuild("${rootProject.projectDir}/mobile-android-pipelines/buildLogic")
