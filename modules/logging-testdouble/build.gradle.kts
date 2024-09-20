import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.pipelines.config.ApkConfig

plugins {
    id("uk.gov.pipelines.android-lib-config")
}

android {
    val apkConfig: ApkConfig by project.rootProject.extra
    defaultConfig {
        namespace = apkConfig.applicationId + ".testdouble"
        compileSdk = apkConfig.sdkVersions.compile
        minSdk = apkConfig.sdkVersions.minimum
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
        libs.androidx.test.core.ktx,
        libs.androidx.test.ext.junit,
        libs.androidx.test.runner,
        libs.hilt.android.testing,
        libs.junit,
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
        kotlin("test"),
        libs.junit.jupiter,
        libs.junit.jupiter.params,
        libs.junit.jupiter.engine,
        platform(libs.junit.bom),
        libs.mockito.kotlin
    ).forEach { dependency ->
        testImplementation(dependency)
    }

    androidTestUtil(libs.androidx.orchestrator)
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
