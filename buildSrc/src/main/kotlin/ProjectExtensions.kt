import org.gradle.api.Project
import org.gradle.process.ExecSpec
import java.io.ByteArrayOutputStream

object ProjectExtensions {
    fun Project.execWithOutput(spec: ExecSpec.() -> Unit) = ByteArrayOutputStream().use { outputStream ->
        exec {
            this.spec()
            this.standardOutput = outputStream
        }
        outputStream.toString().trim()
    }
}