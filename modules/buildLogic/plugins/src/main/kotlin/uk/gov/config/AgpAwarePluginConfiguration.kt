package uk.gov.config

import org.gradle.api.Project

/**
 * Implementation of [PluginConfiguration] that internally applies configuration to both app and
 * library android gradle modules, as per the Android Gradle Plugin (AGP).
 *
 * @param appPluginConfiguration The configuration to apply when the [Project] is an app module.
 *
 * @param libraryPluginConfiguration The configuration to apply when the [Project] is a library
 * module.
 */
data class AgpAwarePluginConfiguration<ExtensionConfig : Any>(
    private val appPluginConfiguration: PluginConfiguration<ExtensionConfig>,
    private val libraryPluginConfiguration: PluginConfiguration<ExtensionConfig>
) : PluginConfiguration<ExtensionConfig> {
    override fun applyConfig(
        project: Project,
        extension: ExtensionConfig
    ) {
        if (
            project.pluginManager.hasPlugin("com.android.application")
        ) {
            appPluginConfiguration.applyConfig(project, extension)
        } else if (
            project.pluginManager.hasPlugin("com.android.library")
        ) {
            libraryPluginConfiguration.applyConfig(project, extension)
        }
    }
}
