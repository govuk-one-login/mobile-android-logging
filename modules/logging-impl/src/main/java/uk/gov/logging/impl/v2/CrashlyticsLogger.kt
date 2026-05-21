package uk.gov.logging.impl.v2

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.recordException
import uk.gov.logging.api.v2.CrashLogger
import uk.gov.logging.api.v2.errorKeys.ErrorKeys

@Deprecated(
    message =
        "Replace with  v3 CrashlyticsLogger" +
            "-aim to remove by 12th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.impl.v3.CrashlyticsLogger",
        ),
    level = DeprecationLevel.WARNING,
)
class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : CrashLogger {
    override fun log(
        throwable: Throwable,
        vararg errorKeys: ErrorKeys,
    ) {
        crashlytics.recordException(throwable) {
            errorKeys.forEach { key(it.key, it.value.toString()) }
        }
    }

    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }
}
