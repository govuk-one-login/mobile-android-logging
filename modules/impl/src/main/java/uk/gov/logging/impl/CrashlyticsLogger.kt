package uk.gov.logging.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import uk.gov.logging.api.CrashLogger

class CrashlyticsLogger @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) : CrashLogger {

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }
}
