package uk.gov.logging.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.CrashLogger
import javax.inject.Inject

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
