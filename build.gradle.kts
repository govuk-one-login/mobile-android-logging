import uk.gov.pipelines.config.ApkConfig
import uk.gov.pipelines.emulator.EmulatorConfig
import uk.gov.pipelines.emulator.SystemImageSource

buildscript {
    val buildLogicDir: String by rootProject.extra("mobile-android-pipelines/buildLogic")

    // Github packages publishing configuration
    val githubRepositoryName: String by rootProject.extra("mobile-android-logging")
    val mavenGroupId: String by rootProject.extra("uk.gov.logging")
    // Sonar configuration
    val sonarProperties: Map<String, String> by rootProject.extra(
        mapOf(
            "sonar.projectKey" to "mobile-android-logging",
            "sonar.projectName" to "mobile-android-logging"
        )
    )

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

val apkConfig by rootProject.extra(
    object: ApkConfig {
        override val applicationId: String = "uk.gov.logging"
        override val debugVersion: String = "DEBUG_VERSION"
        override val sdkVersions = object: ApkConfig.SdkVersions {
            override val minimum = 29
            override val target = 35
            override val compile = 35
        }
    }
)

val emulatorConfig by rootProject.extra(
    EmulatorConfig(
        systemImageSources = setOf(SystemImageSource.GOOGLE_ATD),
        androidApiLevels = setOf(33),
        deviceFilters = setOf("Pixel XL"),
    )
)

plugins {
    id("uk.gov.pipelines.vale-config")
    id("uk.gov.pipelines.sonarqube-root-config")
    alias(libs.plugins.ksp) apply false
}