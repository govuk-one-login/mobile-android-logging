@file:Suppress("ConstPropertyName")

package uk.gov.logging.impl

import uk.gov.logging.api.v2.errorKeys.ErrorKeys
import uk.gov.logging.api.v3.customKeys.CustomKey

object LoggingTestDataRelease {
    private const val throwableMessage = "This is a unit test!"

    const val logMessage = "Unit test log message"
    const val logTag = "Example log tag"
    val logThrowable = Throwable(message = throwableMessage)

    val customKeyNotnull: CustomKey =
        CustomKey.IntKey(
            "key",
            1,
        )

    val multipleCustomKeys =
        listOf(
            CustomKey.IntKey(
                "key",
                1,
            ),
            CustomKey.StringKey(
                "key",
                "",
            ),
        )

    val errorKeysNotNull: ErrorKeys =
        ErrorKeys.IntKey(
            "key",
            1,
        )
}
