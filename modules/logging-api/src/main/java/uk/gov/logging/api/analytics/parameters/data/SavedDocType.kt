package uk.gov.logging.api.analytics.parameters.data

enum class SavedDocType(val value: String) {
    DBS("dbs"),
    NINO("nino"),
    VETERANS_CARD("veterans card"),
    DRIVING_LICENCE("mdl"),
    UNDEFINED("undefined"),
}
