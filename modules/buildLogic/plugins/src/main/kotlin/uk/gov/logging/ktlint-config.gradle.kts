package uk.gov.logging

import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

project.plugins.apply(
    "org.jlleitschuh.gradle.ktlint"
)

configure<KtlintExtension> {
    version = "0.48.2"
    debug = true
    verbose = true
    android = true
    outputToConsole = true
    outputColorName = "RED"
    ignoreFailures = false
    enableExperimentalRules = false
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.JSON)
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        include("**/src/**/*.kt")
    }
}
