package uk.gov.logging.impl.v2

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v2.CrashLogger
import javax.inject.Inject

class CrashlyticsLogger@Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
) : CrashLogger {
    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {

        crashlytics.log(message)
    }
}
