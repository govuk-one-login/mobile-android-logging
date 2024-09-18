import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.pipelines.config.ApkConfig

plugins {
    id("uk.gov.pipelines.android-lib-config")
}

android {
    defaultConfig {
        val apkConfig: ApkConfig by project.rootProject.extra
        namespace = apkConfig.applicationId + ".testdouble"
        compileSdk = apkConfig.sdkVersions.compile
        minSdk = apkConfig.sdkVersions.minimum
        targetSdk = apkConfig.sdkVersions.target
        testInstrumentationRunner = "uk.gov.logging.testdouble.InstrumentationTestRunner"
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

    testOptions {
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
        libs.androidx.test.core.ktx,
        libs.androidx.test.espresso.accessibility,
        libs.androidx.test.runner,
        libs.hilt.android.testing,
        libs.junit,
        libs.mockito.android
    ).forEach {
        androidTestImplementation(it)
    }

    listOf(
        libs.firebase.analytics,
        libs.hilt.android,
        platform(libs.firebase.bom)
    ).forEach {
        implementation(it)
    }

    listOf(
        libs.junit,
        libs.junit.jupiter,
        libs.mockito.kotlin
    ).forEach { dependency ->
        testImplementation(dependency)
    }

    api(projects.modules.loggingApi)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
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
