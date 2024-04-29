import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.logging.config.ApkConfig
import java.net.URI
import uk.gov.logging.extensions.ProjectExtensions.versionName

plugins {
    id("uk.gov.logging.android-lib-config")
    `maven-publish`
}

android {
    defaultConfig {
        namespace = ApkConfig.APPLICATION_ID + ".impl"
        compileSdk = ApkConfig.COMPILE_SDK_VERSION
        minSdk = ApkConfig.MINIMUM_SDK_VERSION
        targetSdk = ApkConfig.TARGET_SDK_VERSION
        testInstrumentationRunner = "uk.gov.logging.impl.InstrumentationTestRunner"
    }

    buildToolsVersion = ApkConfig.ANDROID_BUILD_TOOLS_VERSION

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

    flavorDimensions.add("env")
    productFlavors {
        listOf(
            "build",
            "dev",
            "integration",
            "production",
            "staging"
        ).forEach { flavourString ->
            create(flavourString) {
                dimension = "env"
                val namespaceString = if (flavourString == "production") {
                    "uk.gov.logging.impl"
                } else {
                    "uk.gov.logging.$flavourString.impl"
                }
            }
        }
    }
}

dependencies {
    listOf(
        libs.hilt.android,
        platform(libs.firebase.bom),
        libs.firebase.analytics,
        libs.firebase.crashlytics
    ).forEach {
        implementation(it)
    }
    listOf(
        libs.junit,
        libs.androidx.test.core.ktx,
        libs.androidx.test.ext.junit.ktx,
        libs.androidx.test.runner,
        libs.androidx.test.orchestrator,
        libs.androidx.test.espresso.accessibility,
        libs.androidx.test.espresso.core,
        libs.androidx.test.espresso.contrib,
        libs.androidx.test.espresso.intents,
        libs.hilt.android.testing,
        libs.mockito.kotlin,
        libs.mockito.android
    ).forEach {
        androidTestImplementation(it)
    }

    listOf(
        libs.junit,
        libs.hilt.android.testing,
        libs.junit.jupiter,
        libs.mockito.kotlin,
        project(":modules:testdouble")
    ).forEach { dependency ->
        testImplementation(dependency)
    }

    androidTestUtil(libs.androidx.test.orchestrator)

    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
    api(project(":modules:api"))
}

val verifyAarExistence by project.tasks.registering {
    doLast {
        val expectedFileNames = listOf(
            "impl-debug.aar",
            "impl-release.aar"
        )
        val fileList = project.fileTree(
            "${project.buildDir}/outputs/aar"
        ).files

        val hasGeneratedAllAarFiles = fileList.all { aarFile ->
            aarFile.exists() && aarFile.isFile &&
                    expectedFileNames.contains(aarFile.name)
        }

        if (!hasGeneratedAllAarFiles) {
            throw Exception(
                "There's missing files that are expected to be there!\n" +
                        "Expected files: $expectedFileNames\n" +
                        "File list: $fileList"
            )
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI.create(
                "https://maven.pkg.github.com/govuk-one-login/mobile-android-logging"
            )
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("release") {
            this.groupId = ApkConfig.APPLICATION_ID
            this.artifactId = "logging-impl"
            this.version = project.versionName
            this.artifact(file("${project.buildDir}/outputs/aar/impl-build-release.aar")) {
                this.classifier = "build-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-build-debug.aar")) {
                this.classifier = "build-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-dev-release.aar")) {
                this.classifier = "dev-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-dev-debug.aar")) {
                this.classifier = "dev-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-integration-release.aar")) {
                this.classifier = "integration-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-integration-debug.aar")) {
                this.classifier = "integration-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-staging-release.aar")) {
                this.classifier = "staging-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-staging-debug.aar")) {
                this.classifier = "staging-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-production-release.aar")) {
                this.classifier = "production-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/impl-production-debug.aar")) {
                this.classifier = "production-debug"
                this.extension = "aar"
            }
            pom {
                defaultPomSetup()
            }
        }
    }
}

project.tasks.named("publish").configure {
    mustRunAfter(verifyAarExistence)
    dependsOn(verifyAarExistence)
}

project.afterEvaluate {
    println("MC: Components: ${components.map { it.name }}")
}

fun MavenPom.defaultPomSetup() {
    this.name = "Logging and Analytics Modules for Android Devices"
    this.description = "Gradle configured Android library for fire-and-forget logging and analytics"
    this.url = "http://github.com/govuk-one-login/mobile-android-logging"
    this.licenses {
        this.license {
            this.name = "MIT License"
            this.url = "https://choosealicense.com/licenses/mit/"
        }
    }
    this.developers {
        this.developer {
            this.id = "idCheckTeam"
            this.name = "ID Check Team"
            this.email = "di-dcmaw-id-check-team@digital.cabinet-office.gov.uk"
        }
    }
    this.scm {
        this.connection = "scm:git:git://github.com/govuk-one-login" +
                "/mobile-android-logging.git"
        this.developerConnection = "scm:git:ssh://github.com/govuk-one-login" +
                "/mobile-android-logging.git"
        this.url = "http://github.com/govuk-one-login/mobile-android-logging"
    }
}
