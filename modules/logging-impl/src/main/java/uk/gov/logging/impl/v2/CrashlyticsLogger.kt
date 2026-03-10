package uk.gov.logging.impl.v2

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v2.CrashLogger
import uk.gov.logging.api.v2.errorKeys.ErrorKeys

class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : CrashLogger {
    override fun log(
        throwable: Throwable,
        vararg errorKeys: ErrorKeys,
    ) {
        crashlytics.recordException(throwable)
        errorKeys.forEach {
            it.let {
                crashlytics.setCustomKey(it.key, it.value.toString())
            }
        }
    }

    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }
}
