plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

gradlePlugin {
    plugins {
        register("uk.gov.publishing.config") {
            this.id = this.name
            implementationClass = "uk.gov.publishing.MavenPublishingConfigPlugin"
        }
    }
}

dependencies {
    listOf(
        libs.android.build.tool,
        libs.detekt.gradle,
        libs.kotlin.gradle.plugin,
        libs.ktlint.gradle,
        libs.sonarqube.gradle
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
