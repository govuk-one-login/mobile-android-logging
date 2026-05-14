import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.pipelines.config.ApkConfig

plugins {
    id("uk.gov.pipelines.android-lib-config")
}

android {
    val apkConfig: ApkConfig by project.rootProject.extra
    defaultConfig {
        namespace = apkConfig.applicationId + ".robolectric"
        compileSdk = apkConfig.sdkVersions.compile
        minSdk = apkConfig.sdkVersions.minimum
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }

    testOptions {
        unitTests.all {
            it.testLogging {
                events =
                    setOf(
                        TestLogEvent.FAILED,
                        TestLogEvent.PASSED,
                        TestLogEvent.SKIPPED,
                    )
            }
        }
    }
}

dependencies {
    implementation(platform(libs.kotlin.bom))

    listOf(
        libs.junit,
        libs.org.robolectric,
    ).forEach {
        api(it)
    }

    testImplementation(libs.hamcrest)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "Robolectric test utilities for Logging",
        )
        description.set(
            """
            Robolectric test utilities for the Android logging library.
            """.trimIndent(),
        )
    }
}
