buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}


val apkConfig by rootProject.extra {
    debugVersion = "DEBUG_VERSION"
    /*mapOf(
        "application.id" to "uk.gov.logging",
        "sdk.version.min" to "29",
        "sdk.version.target" to "33",
        "sdk.version.compile" to "34",
        "debug_version" to "DEBUG_VERSION"
    )*/
}

plugins {
    id("uk.gov.pipelines.vale-config")
    id("uk.gov.pipelines.sonarqube-root-config")
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.android.application) apply false
}