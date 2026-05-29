package uk.gov.logging.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.CrashLogger
import javax.inject.Inject

@Deprecated(
    message =
        "Replace with  v3 CrashlyticsLogger" +
            "-aim to remove by 27th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.impl.v3.CrashlyticsLogger",
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
