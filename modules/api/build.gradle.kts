import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.logging.config.ApkConfig
import java.net.URI
import uk.gov.logging.extensions.ProjectExtensions.versionName

plugins {
    `maven-publish`
    id("uk.gov.logging.android-lib-config")
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        namespace = ApkConfig.APPLICATION_ID + ".api"
        compileSdk = ApkConfig.COMPILE_SDK_VERSION
        minSdk = ApkConfig.MINIMUM_SDK_VERSION
        targetSdk = ApkConfig.TARGET_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
                    "uk.gov.logging.api"
                } else {
                    "uk.gov.logging.$flavourString.api"
                }
                buildConfigField(
                    "String",
                    "NAME_SPACE",
                    "\"$namespaceString\""
                )
                manifestPlaceholders["namespace"] = namespaceString
            }
        }
    }
}

dependencies {
    listOf(
        libs.firebase.android,
        libs.hilt.android,
        libs.com.android.tools.build.gradle,
        libs.firebase.android,
        platform(libs.firebase.bom),
        libs.firebase.analytics,
        libs.ktor.client.core
    ).forEach {
        implementation(it)
}

    listOf(
        libs.androidx.test.core.ktx,
        libs.androidx.test.ext.junit.ktx,
        libs.androidx.test.runner,
        libs.androidx.test.rules,
        libs.androidx.test.orchestrator,
        libs.androidx.test.espresso.accessibility,
        libs.androidx.test.espresso.core,
        libs.androidx.test.espresso.contrib,
        libs.androidx.test.espresso.intents,
        libs.androidx.test.uiAutomator,
        libs.hilt.android.testing,
        libs.mockito.kotlin
    ).forEach {
        androidTestImplementation(it)
    }

    listOf(
        libs.junit,
        libs.hilt.android.testing,
        libs.junit.jupiter
    ).forEach { dependency ->
        testImplementation(dependency)
    }

    androidTestUtil(libs.androidx.test.orchestrator)

    listOf(
        libs.hilt.compiler
    ).forEach {
        kapt(it)
        kaptAndroidTest(it)
        kaptTest(it)
    }

    kapt(libs.lifecycle.compiler)
}

val verifyAarExistence by project.tasks.registering {
    doLast {
        val expectedFileNames = listOf(
            "api-debug.aar",
            "api-release.aar"
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

dependencyCheck {
    analyzers.assemblyEnabled=false
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
            this.artifactId = "mobile-android-logging"
            this.version = project.versionName
            this.artifact(file("${project.buildDir}/outputs/aar/api-release.aar")) {
                this.classifier = "release"
                this.extension = "aar"
            }
            this.artifact(file("${project.buildDir}/outputs/aar/api-debug.aar")) {
                this.classifier = "debug"
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

kapt {
    correctErrorTypes = true
}
