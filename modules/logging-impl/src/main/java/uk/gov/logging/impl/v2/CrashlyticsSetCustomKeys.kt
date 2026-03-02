package uk.gov.logging.impl.v2

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v2.CrashLogger
import uk.gov.logging.api.v2.SetCustomKeys
import uk.gov.logging.api.v2.errorKeys.ErrorKeys
import javax.inject.Inject

class CrashlyticsSetCustomKeys @Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
) : SetCustomKeys {
    override fun setKeys(vararg errorKeys: ErrorKeys, value: Any) {
        errorKeys.forEach { errorKey ->
            crashlytics.setCustomKey(errorKey.key,errorKey.value.toString())
        }
    }
}
