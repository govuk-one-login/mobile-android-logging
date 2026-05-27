package uk.gov.logging.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.CrashLogger
import javax.inject.Inject

@Deprecated(
    message =
        "Replace with v2 version which allows to set custom error keys" +
            "-aim to remove by 12th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.impl.v2.CrashlyticsLogger",
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
