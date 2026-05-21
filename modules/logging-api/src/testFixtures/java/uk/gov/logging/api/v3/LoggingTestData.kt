package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customkey.ErrorKeys

object LoggingTestData {
    private const val THROWABLE_MESSAGE = "This is a unit test!"

    const val LOG_MESSAGE = "Unit test log message"
    const val LOG_TAG = "Example log tag"

    val logThrowable = Throwable(message = THROWABLE_MESSAGE)

    val errorKeys = ErrorKeys(component = "test.component", action = "test.action")

    val debug: LogEntry =
        LogEntry.Debug(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
        )

    val info: LogEntry =
        LogEntry.Info(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
        )

    val warning: LogEntry =
        LogEntry.Warn(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
        )

    val error: LogEntry.Exception =
        LogEntry.Error(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            throwable = logThrowable,
        )

    val errorWithCustomKey: LogEntry.Exception =
        LogEntry.Error(
            message = LOG_MESSAGE,
            tag = LOG_TAG,
            throwable = logThrowable,
            errorKeys = errorKeys,
        )

    val errorEntries =
        listOf(
            error,
            errorWithCustomKey,
        )

    val messageEntries =
        listOf<LogEntry>(
            LogEntry.Warn(
                message = LOG_MESSAGE,
                tag = LOG_TAG,
            ),
            LogEntry.Info(
                message = LOG_MESSAGE,
                tag = LOG_TAG,
            ),
            LogEntry.Debug(
                message = LOG_MESSAGE,
                tag = LOG_TAG,
            ),
            LogEntry.Verbose(
                message = LOG_MESSAGE,
                tag = LOG_TAG,
            ),
        )
}
