package uk.gov.publishing

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import uk.gov.publishing.MavenPublishingConfigExtension.Companion.mavenPublishingConfig
import uk.gov.publishing.defaults.MavenPublishingConfigDefaults

/**
 * Custom convention plugin that uses [MavenPublishingConfigExtension] to configure the
 * [com.android.build.api.dsl.PublishingOptions] and the [PublishingExtension] provided by the
 * maven publishing plugin.
 *
 * Applies the defaults defined within [MavenPublishingConfigDefaults.defaultSettings].
 *
 * @see MavenPublishingConfigDefaults.defaultSettings
 * @see uk.gov.config.PluginConfiguration
 */
class MavenPublishingConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        target.run {
            plugins.apply("maven-publish")

            val extensionConfig: MavenPublishingConfigExtension = mavenPublishingConfig()

            MavenPublishingConfigDefaults.defaultSettings.applyConfig(project, extensionConfig)
        }
}
