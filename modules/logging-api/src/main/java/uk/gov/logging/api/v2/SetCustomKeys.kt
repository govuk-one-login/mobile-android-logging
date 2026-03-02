package uk.gov.logging.api.v2

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

interface SetCustomKeys {
    fun setKeys (vararg errorKeys: ErrorKeys,value: Any)
}
