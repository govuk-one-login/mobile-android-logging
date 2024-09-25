import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.pipelines.config.ApkConfig

plugins {
    id("uk.gov.pipelines.android-lib-config")
}

android {
    buildFeatures {
        buildConfig = true
    }

    val apkConfig: ApkConfig by project.rootProject.extra
    defaultConfig {
        namespace = apkConfig.applicationId + ".api"
        compileSdk = apkConfig.sdkVersions.compile
        minSdk = apkConfig.sdkVersions.minimum
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildToolsVersion = libs.versions.android.build.tools.get()

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        targetSdk = apkConfig.sdkVersions.target
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
        unitTests.all {
            it.useJUnitPlatform()
            it.testLogging {
                events = setOf(
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED
                )
            }
        }
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    listOf(
        libs.firebase.analytics,
        libs.hilt.android,
        libs.ktor.client.core,
        platform(libs.firebase.bom)
    ).forEach {
        implementation(it)
    }

    listOf(
        libs.hilt.android.testing,
        kotlin("test"),
        libs.junit.jupiter,
        libs.junit.jupiter.params,
        libs.junit.jupiter.engine,
        platform(libs.junit.bom),
    ).forEach { dependency ->
        testImplementation(dependency)
    }

    listOf(
        libs.hilt.compiler
    ).forEach {
        kapt(it)
        kaptTest(it)
    }
}

kapt {
    correctErrorTypes = true
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "Logging and Analytics Modules for Android Devices"
        )
        description.set(
            """
                Gradle configured Android library for fire-and-forget logging and analytics.
            """.trimIndent()
        )
    }
}
