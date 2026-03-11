package uk.gov.logging.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.CrashLogger
import javax.inject.Inject

@Deprecated(
    message =
        "Replace with v2 version which allows to set custom error keys" +
            "-aim to remove by 10th of May 26 2026",
    replaceWith =
        ReplaceWith(
            "mobile-android-logging/modules/logging-impl/src/main/" +
                "java/uk/gov/logging/impl/v2/CrashlyticsLogger.kt",
        ),
    level = DeprecationLevel.WARNING,
)
class CrashlyticsLogger @Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
) : CrashLogger {
    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }
}
