package uk.gov.publishing.defaults

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import uk.gov.config.AgpAwarePluginConfiguration
import uk.gov.config.PluginConfiguration
import uk.gov.logging.extensions.ProjectExtensions.versionName
import uk.gov.publishing.MavenPublishingConfigExtension
import uk.gov.publishing.MavenPublishingConfigPlugin
import uk.gov.publishing.handlers.MavenPublishingConfigHandler
import java.net.URI

object MavenPublishingConfigDefaults {
    /**
     * Performs no action as part of the configuration phase.
     */
    private val nothing =
        PluginConfiguration<MavenPublishingConfigExtension> { _, _ ->
            // do nothing
        }

    /**
     * The behaviour to perform when applying the [MavenPublishingConfigPlugin] to an Android
     * library module.
     *
     * Configures [LibraryExtension.publishing] to create all variants with java docs and sources.
     * Also configures [PublishingExtension] to register the `default` component generated in
     * [LibraryExtension.publishing] to capture all required variants.
     */
    private val defaultLibConfig =
        PluginConfiguration {
                project: Project,
                extension: MavenPublishingConfigExtension
            ->

            project.configure<LibraryExtension> {
                configureAndroidPublishing()
            }

            project.configure<PublishingExtension> {
                configureMavenPublishing(project, extension)
            }
        }

    /**
     * Declares the repositories to utilise for the [PublishingExtension.repositories] block.
     *
     * Also registers a `default` publication that's generated via [configureAndroidPublishing],
     * using the [Project.getName] value as the artifact ID.
     */
    private fun PublishingExtension.configureMavenPublishing(
        project: Project,
        extension: MavenPublishingConfigExtension
    ) {
        repositories {
            configureMavenRepositoriesToPublishTo(project)
        }
        publications {
            register<MavenPublication>("default") {
                this.groupId = extension.mavenConfigBlock.artifactGroupId.get()
                this.artifactId = project.name
                this.version = project.versionName

                project.afterEvaluate {
                    from(project.components["default"])
                    withBuildIdentifier()
                }
                pom {
                    defaultPomSetup(
                        extension.mavenConfigBlock
                    )
                }
            }
        }
    }

    /**
     * Configures the [LibraryExtension.publishing] property to declare all applicable variants
     * for a given Android library module. This internally creates the `default` component.
     */
    private fun LibraryExtension.configureAndroidPublishing() {
        publishing {
            multipleVariants {
                allVariants() // creates the 'default' component
                withSourcesJar() // creates a Java Archive (JAR) with the source code
                withJavadocJar() // creates a JAR with the java documentation
            }
        }
    }

    /**
     * Configures the locations to publish to:
     *
     * - The One Login GitHub packages registry
     * - The `project.buildDir/repo` directory for locally verifying the contents that'll upload
     *   to the GitHub packages registry.
     */
    private fun RepositoryHandler.configureMavenRepositoriesToPublishTo(project: Project) {
        maven {
            name = "GitHubPackages"
            url =
                URI.create(
                    "https://maven.pkg.github.com/govuk-one-login/mobile-android-logging"
                )
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "localBuild"
            url = project.uri("${project.buildDir}/repo")
        }
    }

    /**
     * The default settings to apply for the [MavenPublishingConfigPlugin].
     *
     * No action happens for Android app modules, as this is instead uploaded to the google play
     * console, instead of the GitHub packages registry.
     */
    val defaultSettings =
        AgpAwarePluginConfiguration(
            appPluginConfiguration = nothing,
            libraryPluginConfiguration = defaultLibConfig
        )

    /**
     * Configures the generated Maven Project Object Model (POM) based on the [extension]
     * configuration.
     */
    private fun MavenPom.defaultPomSetup(extension: MavenPublishingConfigHandler) {
        this.name.set(extension.name)
        this.description.set(extension.description)
        this.url.set(extension.url)
        this.licenses {
            this.license {
                this.name.set(extension.license.name)
                this.url.set(extension.license.url)
            }
        }
        this.developers {
            this.developer {
                this.id.set(extension.developer.id)
                this.name.set(extension.developer.name)
                this.email.set(extension.developer.email)
            }
        }
        this.scm {
            this.connection.set(extension.scm.connection)
            this.developerConnection.set(extension.scm.developerConnection)
            this.url.set(extension.scm.url)
        }
    }
}
