package uk.gov.logging.api.analytics.parameters.data

enum class TaxonomyLevel3(val value: String) {
    PASSPORT_CRI("passport cri"),
    DRIVING_LICENCE_CRI("driving licence cri"),
    BRP_CRI("brp cri"),
    BROWSE_WALLET("browse wallet"),
    VIEW_CREDENTIAL("view credential"),
    SIGN_IN("sign in"),
    RE_AUTH("re auth"),
    UNDEFINED("undefined")
}
