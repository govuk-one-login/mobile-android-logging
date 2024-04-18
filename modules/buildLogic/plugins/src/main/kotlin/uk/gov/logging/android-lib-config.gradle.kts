package uk.gov.logging

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import uk.gov.logging.extensions.BaseExtensions.baseAndroidConfig
import uk.gov.logging.extensions.LintExtensions.configureLintOptions

listOf(
    "com.android.library",
    "org.jetbrains.kotlin.android" ,
    "uk.gov.logging.jvm-toolchains",
    "uk.gov.logging.emulator-config",
    "uk.gov.logging.ktlint-config",
    "uk.gov.logging.detekt-config",
    "org.owasp.dependencycheck"
).forEach {
    project.plugins.apply(it)
}

configure<BaseExtension> {
    baseAndroidConfig(project)
}

configure<LibraryExtension> {
    lint(configureLintOptions("${rootProject.projectDir}/config"))
}
