import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.pipelines.config.ApkConfig

plugins {
    id("uk.gov.pipelines.android-lib-config")
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val apkConfig: ApkConfig by project.rootProject.extra
        namespace = apkConfig.applicationId + ".api"
        compileSdk = apkConfig.sdkVersions.compile
        minSdk = apkConfig.sdkVersions.minimum
        targetSdk = apkConfig.sdkVersions.target
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
        libs.androidx.test.espresso.contrib,
        libs.androidx.test.espresso.core,
        libs.androidx.test.espresso.intents,
        libs.androidx.test.ext.junit.ktx,
        libs.androidx.test.orchestrator,
        libs.androidx.test.rules,
        libs.androidx.test.runner,
        libs.androidx.test.uiAutomator,
        libs.hilt.android.testing,
        libs.mockito.kotlin
    ).forEach {
        androidTestImplementation(it)
    }

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
        libs.junit,
        libs.junit.jupiter
    ).forEach { dependency ->
        testImplementation(dependency)
    }

    listOf(
        libs.hilt.compiler
    ).forEach {
        kapt(it)
        kaptAndroidTest(it)
        kaptTest(it)
    }

    androidTestUtil(libs.androidx.test.orchestrator)
    kapt(libs.lifecycle.compiler)
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
