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
        namespace = ApkConfig.APPLICATION_ID + ".testdouble"
        compileSdk = ApkConfig.COMPILE_SDK_VERSION
        minSdk = ApkConfig.MINIMUM_SDK_VERSION
        targetSdk = ApkConfig.TARGET_SDK_VERSION
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
                    "uk.gov.logging.testdouble"
                } else {
                    "uk.gov.logging.$flavourString.testdouble"
                }
            }
        }
    }
}

dependencies {
    listOf(
        libs.androidx.test.core.ktx,
        libs.androidx.test.espresso.accessibility,
        libs.androidx.test.ext.junit.ktx,
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

    androidTestUtil(libs.androidx.test.orchestrator)
    api(project(":modules:api"))
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
}

val verifyAarExistence by project.tasks.registering {
    doLast {
        val expectedFileNames = listOf(
            "testdouble-build-debug.aar",
            "testdouble-build-release.aar",
            "testdouble-dev-debug.aar",
            "testdouble-dev-release.aar",
            "testdouble-integration-debug.aar",
            "testdouble-integration-release.aar",
            "testdouble-staging-debug.aar",
            "testdouble-staging-release.aar",
            "testdouble-production-debug.aar",
            "testdouble-production-release.aar"
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
            this.artifactId = "logging-testdouble"
            this.version = project.versionName
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-build-release.aar")) {
                this.classifier = "build-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-build-debug.aar")) {
                this.classifier = "build-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-dev-release.aar")) {
                this.classifier = "dev-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-dev-debug.aar")) {
                this.classifier = "dev-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-integration-release.aar")) {
                this.classifier = "integration-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-integration-debug.aar")) {
                this.classifier = "integration-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-staging-release.aar")) {
                this.classifier = "staging-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-staging-debug.aar")) {
                this.classifier = "staging-debug"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-production-release.aar")) {
                this.classifier = "production-release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/testdouble-production-debug.aar")) {
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