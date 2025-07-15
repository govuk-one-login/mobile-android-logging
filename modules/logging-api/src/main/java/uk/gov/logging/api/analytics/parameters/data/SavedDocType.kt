package uk.gov.logging.api.analytics.parameters.data

enum class SavedDocType(val value: String) {
    DBS("dbs"),
    NINO("nino"),
    VETERAN_CARD("veteran card"),
    DRIVING_LICENCE("mdl"),
    UNDEFINED("undefined"),
}
