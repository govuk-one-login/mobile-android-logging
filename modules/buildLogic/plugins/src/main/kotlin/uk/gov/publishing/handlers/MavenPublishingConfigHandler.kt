package uk.gov.publishing.handlers

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.publish.maven.MavenPomDeveloper
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPomLicense
import org.gradle.api.publish.maven.MavenPomLicenseSpec
import org.gradle.api.publish.maven.MavenPomScm
import uk.gov.logging.config.ApkConfig
import javax.inject.Inject

/**
 * Configuration specific to setting up a Maven Project Object Model (POM) file for publishing
 * purposes.
 *
 * Initialises with convention (default) values via [setPropertyConventions].
 *
 * The [name] and [description] properties are manually declared on a per-gradle-module basis.
 */
open class MavenPublishingConfigHandler
@Inject
constructor(
    objects: ObjectFactory
) : MavenPomDeveloperSpec,
    MavenPomLicenseSpec {
    val artifactGroupId: Property<String> = objects.property(String::class.java)

    val description: Property<String> = objects.property(String::class.java)
    val name: Property<String> = objects.property(String::class.java)

    val url: Property<String> = objects.property(String::class.java)
    val developer: MavenPomDeveloper = objects.newInstance(MavenPomDeveloper::class.java)
    val license: MavenPomLicense = objects.newInstance(MavenPomLicense::class.java)
    val scm: MavenPomScm = objects.newInstance(MavenPomScm::class.java)

    init {
        setPropertyConventions()
    }

    override fun developer(action: Action<in MavenPomDeveloper>?) {
        action?.execute(developer)
    }

    override fun license(action: Action<in MavenPomLicense>?) {
        action?.execute(license)
    }

    fun scm(action: Action<in MavenPomScm>?) {
        action?.execute(scm)
    }

    private fun setPropertyConventions() {
        artifactGroupId.convention(ApkConfig.APPLICATION_ID)

        url.convention(
            "https://github.com/govuk-one-login/mobile-android-logging"
        )

        developer.id.convention("idCheckTeam")
        developer.name.convention("ID Check Team")
        developer.email.convention(
            "di-dcmaw-id-check-team@digital.cabinet-office.gov.uk"
        )

        license.name.convention("MIT License")
        license.url.convention("https://choosealicense.com/licenses/mit/")

        scm.connection.convention(
            "scm:git:git://github.com/govuk-one-login" +
                "/mobile-android-logging.git"
        )
        scm.developerConnection.convention(
            "scm:git:ssh://github.com/govuk-one-login" +
                "/mobile-android-logging.git"
        )
        scm.url.convention(
            "http://github.com/govuk-one-login/mobile-android-logging"
        )
    }
}
