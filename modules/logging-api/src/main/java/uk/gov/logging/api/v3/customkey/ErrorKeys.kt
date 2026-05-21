package uk.gov.logging.api.v3.customkey

import java.util.Objects

/**
 * Custom keys to be associated with an exception.
 *
 * @param component where the error occurred
 *   e.g. `wallet.datastore`, `idcheck.face_scanner`, `app.splash_screen`
 * @param action the action being performed when the error occurred
 *   e.g. `wallet.add_credential` , `idcheck.verify_face`, `app.check_app_info`
 */
class ErrorKeys(
    val component: String? = null,
    val action: String? = null,
) {
    override fun equals(other: Any?): Boolean =
        other is ErrorKeys &&
            component == other.component &&
            action == other.action

    override fun hashCode(): Int = Objects.hash(component, action)

    override fun toString(): String = "ErrorKeys(component=$component, action=$action)"
}
