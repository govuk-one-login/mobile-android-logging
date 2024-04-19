plugins {
    `kotlin-dsl`
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

dependencies {
    listOf(
        libs.android.build.tool,
        libs.ktlint.gradle,
        libs.detekt.gradle,
        libs.kotlin.gradle.plugin,
        libs.dependencycheck
    ).forEach { dependency ->
        implementation(dependency)
    }
}

kotlin { jvmToolchain(17) }

ktlint {
    filter {
        exclude { it.file.absolutePath.contains("/build/") }
    }
}
