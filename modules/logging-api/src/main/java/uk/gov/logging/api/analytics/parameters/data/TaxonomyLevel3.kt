package uk.gov.logging.api.analytics.parameters.data

enum class TaxonomyLevel3(val value: String) {
    PASSPORT_CRI("passport cri"),
    DRIVING_LICENCE_CRI("driving licence cri"),
    BRP_CRI("brp cri"),
    MANUAL_LINK("manual linking"),
    WALLET_LIBRARY("wallet library"),
    VIEW_DOCUMENT("view document"),
    ADD_DOCUMENT("add document"),
    MANAGE_ACCOUNT("manage account"),
    DELETE_ACCOUNT("delete account"),
    SIGN_IN("sign in"),
    SIGN_OUT("sign out"),
    RE_AUTH("re auth"),
    RESUME("resume"),
    DOCUMENT_SELECTION("document selection"),
    UNLOCK("unlock"),
    BIOMETRICS("biometrics"),
    UNDEFINED("undefined"),
    ERROR("error"),
    LICENCES("licences")
}
