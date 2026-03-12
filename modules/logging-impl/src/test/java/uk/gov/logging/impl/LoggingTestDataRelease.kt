@file:Suppress("ConstPropertyName")

package uk.gov.logging.impl

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

object LoggingTestDataRelease {
    private const val throwableMessage = "This is a unit test!"

    const val logMessage = "Unit test log message"
    const val logTag = "Example log tag"
    val logThrowable = Throwable(message = throwableMessage)

    val errorKeysNotnull: ErrorKeys =
        ErrorKeys.IntKey(
            "key",
            1,
        )
}
