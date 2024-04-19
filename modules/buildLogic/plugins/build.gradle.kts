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
    ).forEach { dependency ->
        implementation(dependency)
    }
}

kotlin { jvmToolchain(17) }
