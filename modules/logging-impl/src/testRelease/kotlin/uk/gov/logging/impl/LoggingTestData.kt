package uk.gov.logging.impl

object LoggingTestData {
    private const val throwableMessage = "This is a unit test!"

    const val logMessage = "Unit test log message"
    const val logTag = "Example log tag"
    val logThrowable = Throwable(message = throwableMessage)
}
