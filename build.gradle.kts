buildscript {
//    val minAndroidVersion by rootProject.extra { 29 }
//    val compileAndroidVersion by rootProject.extra { 34 }
//    val androidBuildToolsVersion by rootProject.extra { "34.0.0" }
//    val composeKotlinCompilerVersion by rootProject.extra { "1.5.0" }
//    val configDir by rootProject.extra { "$rootDir/config" }
//    val baseNamespace by rootProject.extra { "uk.gov.android.ui" }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("uk.gov.logging.vale-config")
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}
