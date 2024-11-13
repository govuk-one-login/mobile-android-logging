package uk.gov.logging.testdouble

object LoggingTestData {
    private const val throwableMessage = "This is a unit test!"

    const val logMessage = "Unit test log message"
    const val logTag = "Example log tag"
    val logThrowable = Throwable(message = throwableMessage)

    val logMessageEntry = LogEntry.Message(
        tag = logTag,
        message = logMessage,
    )
    val logErrorEntry = LogEntry.Error(
        tag = logTag,
        message = logMessage,
        throwable = logThrowable,
    )
}
