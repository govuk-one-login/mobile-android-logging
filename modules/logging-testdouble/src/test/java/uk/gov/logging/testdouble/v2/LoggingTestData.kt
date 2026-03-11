package uk.gov.logging.testdouble.v2

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

object LoggingTestData {
    private const val THROWABLE_MESSAGE = "This is a unit test!"

    const val LOG_MESSAGE = "Unit test log message"

    const val LOG_MESSAGE_TWO = "unit test second log message"
    const val LOG_TAG = "Example log tag"

    const val LOG_TAG_TWO = "Example log tag two"
    val logThrowable = Throwable(message = THROWABLE_MESSAGE)

    val errorKeys: ErrorKeys =
        ErrorKeys.IntKey(
            "key",
            1,
        )

    val logMessageEntry =
        LogEntry.Message(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
        )
    val logErrorEntry =
        LogEntry.Error(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            throwable = logThrowable,
            errorKeys,
        )
    val logErrorEntryNoKeys =
        LogEntry.ErrorNoKeys(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            throwable = logThrowable,
        )

    val logMessageEntryFalse =
        LogEntry.Message(
            tag = LOG_TAG,
            message = LOG_MESSAGE_TWO,
        )
    val logTagEntryFalse =
        LogEntry.Message(
            tag = LOG_TAG_TWO,
            message = LOG_MESSAGE,
        )
}
