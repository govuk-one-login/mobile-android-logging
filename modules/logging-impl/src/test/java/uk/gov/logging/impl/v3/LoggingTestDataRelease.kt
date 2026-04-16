package uk.gov.logging.impl.v3

import uk.gov.logging.api.v3.customkey.CustomKey

object LoggingTestDataRelease {
    const val LOG_MESSAGE = "Unit test log message"
    const val LOG_TAG = "Example log tag"
    val logThrowable = Throwable(message = "This is a unit test!")

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
}
