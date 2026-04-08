package uk.gov.logging.impl.v3

object LoggingTestDataRelease {
    private const val THROWABLE_MESSAGE = "This is a unit test!"

    const val LOG_MESSAGE = "Unit test log message"
    const val LOG_TAG = "Example log tag"
    val logThrowable = Throwable(message = THROWABLE_MESSAGE)
}
