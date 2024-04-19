package uk.gov.logging

import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

project.plugins.apply(
    "org.jlleitschuh.gradle.ktlint"
)

configure<KtlintExtension> {
    version.set("0.48.2")
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)
    enableExperimentalRules.set(false)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.JSON)
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude(
            "**/generated/**",
            "**/build/**",
            "${project.projectDir}/build/**/*",
            "${project.projectDir}/**/build/**/*",
            "/Users/runner/work/mobile-android-logging/mobile-android-logging/modules/buildLogic/plugins/build/generated-sources/kotlin-dsl-plugins/kotlin/uk/gov/logging/*"
        )
        setIncludes(listOf("${project.projectDir}/src/**/*.kt"))
    }
}
