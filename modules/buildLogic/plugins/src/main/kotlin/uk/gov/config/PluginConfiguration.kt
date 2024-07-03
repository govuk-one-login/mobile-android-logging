package uk.gov.config

import org.gradle.api.Project

/**
 * Functional interface for configuring a part of a custom gradle plugin.
 */
fun interface PluginConfiguration<ExtensionConfig : Any> {
    /**
     * Configures the [project], using [extension] for potential inputs.
     *
     * @see uk.gov.publishing.defaults.MavenPublishingConfigDefaults.defaultSettings
     */
    fun applyConfig(
        project: Project,
        extension: ExtensionConfig
    )
}
