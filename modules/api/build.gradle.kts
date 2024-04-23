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
            android.libraryVariants.forEach { variant ->
                this.artifact(file("${project.buildDir}/outputs/aar/sdk-${variant.name}.aar"))
            }
            pom {
                defaultPomSetup()
            }
        }
    }
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
