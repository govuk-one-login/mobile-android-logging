package uk.gov.logging.api.v3

import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.v3.customkey.ErrorKeys

/**
 * [LogEntry] has data class and a builder.
 * The [LogTagProvider] interface can be implemented to avoid having to pass tags.
 * This example shows different ways to use a [Logger].
 * It also shows the different
 * preset [LogEntry] types that are available.
 *
 * Note that [LoggerExample] implements [LogTagProvider], allowing the caller to log
 * without specifying the tag manually.
 */
internal class LoggerExample(
    val logger: Logger,
) : LogTagProvider {
    fun example() {
        // Log at different levels
        logger.verbose(tag, "verbose message")
        logger.debug(tag, "debug message")
        logger.info(tag, "info message")
        logger.warning(tag, "warning message")
        logger.error(tag, "error message", RuntimeException("error"))

        // If the container is a LogTagProvider then no tag is needed
        logger.verbose("verbose message")
        logger.debug("debug message")
        logger.info("info message")
        logger.warning("warning message")
        logger.error("error message", RuntimeException("error"))

        // There is also a low level log() function
        // Each log level has a corresponding LogEntry type
        logger.log(LogEntry.Verbose(tag, "verbose log"))
        logger.log(LogEntry.Debug(tag, "debug log"))
        logger.log(LogEntry.Info(tag, "info log"))
        logger.log(LogEntry.Warn(tag, "warn log"))
        logger.log(LogEntry.Error(tag, "error log", RuntimeException("error")))

        // Logger examples using vararg aspect of the Logger interface
        logger.log(LogEntry.Info(tag, "info log"), LogEntry.Debug(tag, "debug log"))

        // Log entries may be completely customized if needed
        logger.log(
            object : LogEntry.Message {
                override val level: LogLevel = LogLevel.Warn
                override val message: String = "Custom warning message"
                override val tag: String = "Custom tag"
            },
        )

        logger.log(
            object : LogEntry.Exception {
                override val level: LogLevel = LogLevel.Warn
                override val message: String = "Custom warning message"
                override val tag: String = "Custom tag"
                override val errorKeys: ErrorKeys = ErrorKeys(component = "app.component")
                override val throwable: Throwable = RuntimeException("error")
            },
        )
    }
}
