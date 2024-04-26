buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("uk.gov.logging.vale-config")
    id("uk.gov.logging.sonarqube-root-config")
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.services) apply false
}
