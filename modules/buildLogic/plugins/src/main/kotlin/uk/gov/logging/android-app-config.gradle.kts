package uk.gov.logging

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import uk.gov.logging.extensions.BaseExtensions.baseAndroidConfig
import uk.gov.logging.extensions.LintExtensions.configureLintOptions

listOf(
    "com.android.application",
    "org.jetbrains.kotlin.android",
    "org.owasp.dependencycheck",
    "org.jetbrains.kotlin.plugin.serialization",
//    "com.google.gms.google-services",
    "kotlin-kapt",
    "org.jlleitschuh.gradle.ktlint",
    "uk.gov.logging.jvm-toolchains",
    "uk.gov.logging.emulator-config",
    "uk.gov.logging.ktlint-config",
    "uk.gov.logging.detekt-config",
    "uk.gov.logging.jacoco-app-config"
).forEach {
    project.plugins.apply(it)
}

configure<BaseExtension> {
    baseAndroidConfig(project)
}

configure<ApplicationExtension> {
    lint(configureLintOptions("${rootProject.projectDir}/config"))
}
