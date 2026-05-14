package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customkey.CustomKey

object LoggingTestData {
    private const val THROWABLE_MESSAGE = "This is a unit test!"

    const val LOG_MESSAGE = "Unit test log message"
    const val LOG_TAG = "Example log tag"

    val logThrowable = Throwable(message = THROWABLE_MESSAGE)

    val intCustomKey = CustomKey.IntKey("Key", 1)

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
            customKeys = listOf(intCustomKey),
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
