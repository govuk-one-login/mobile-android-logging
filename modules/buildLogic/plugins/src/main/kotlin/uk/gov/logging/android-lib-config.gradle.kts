package uk.gov.logging

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import uk.gov.logging.extensions.BaseExtensions.baseAndroidConfig
import uk.gov.logging.extensions.LintExtensions.configureLintOptions

listOf(
    "com.android.library",
    "kotlin-kapt",
    "org.jetbrains.kotlin.android",
    "uk.gov.logging.detekt-config",
    "uk.gov.logging.emulator-config",
    "uk.gov.logging.jacoco-lib-config",
    "uk.gov.logging.jvm-toolchains",
    "uk.gov.logging.ktlint-config",
    "uk.gov.logging.sonarqube-module-config",
    "uk.gov.publishing.config"
).forEach {
    project.plugins.apply(it)
}

configure<BaseExtension> {
    baseAndroidConfig(project)
}

configure<LibraryExtension> {
    lint(configureLintOptions("${rootProject.projectDir}/config"))
}
