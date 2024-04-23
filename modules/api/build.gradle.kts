import uk.gov.logging.config.ApkConfig
import java.net.URI
import uk.gov.logging.extensions.ProjectExtensions.versionName

plugins {
    id("uk.gov.logging.android-lib-config")
    `maven-publish`
}

android {
    namespace = ApkConfig.APPLICATION_ID + ".api"
}

dependencies {
    listOf(
        libs.androidx.test.core.ktx,
        libs.androidx.test.ext.junit.ktx,
        libs.androidx.test.runner,
        libs.androidx.test.orchestrator
    ).forEach {
        androidTestImplementation(it)
    }

    androidTestUtil(libs.androidx.test.orchestrator)

    listOf(
        libs.junit
    ).forEach { dependency ->
        androidTestImplementation(dependency)
        testImplementation(dependency)
    }
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
