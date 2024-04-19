package uk.gov.logging.extensions

import org.gradle.api.Project
import org.gradle.process.ExecSpec
import uk.gov.logging.config.ApkConfig
import uk.gov.logging.output.OutputStreamGroup
import java.io.ByteArrayOutputStream

object ProjectExtensions {
    fun Project.execWithOutput(spec: ExecSpec.() -> Unit) =
        OutputStreamGroup().use { outputStreamGroup ->
            val byteArrayOutputStream = ByteArrayOutputStream()
            outputStreamGroup.add(byteArrayOutputStream)
            exec {
                this.spec()
                outputStreamGroup.add(this.standardOutput)
                this.standardOutput = outputStreamGroup
            }
            byteArrayOutputStream.toString().trim()
        }

    val Project.versionCode
        get() = prop("versionCode", Integer.MAX_VALUE).toInt()
    val Project.versionName
        get() = prop("versionName", ApkConfig.DEBUG_VERSION)

    private fun Project.prop(key: String, default: Any): String {
        return providers.gradleProperty(key).getOrElse(default.toString())
    }

    fun Project.debugLog(messageSuffix: String) {
        logger.debug("${project.path}: $messageSuffix")
    }
}
