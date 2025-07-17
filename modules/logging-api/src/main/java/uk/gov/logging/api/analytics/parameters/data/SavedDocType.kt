package uk.gov.logging.api.analytics.parameters.data

/**
 * Only used in ID Check SDK,
 * This is not to be used with v3.1 saved_doc_type (Wallet only)
 */
enum class SavedDocType(val value: String) {
    PASSPORT("passport"),
    BRP("brp"),
    DRIVING_LICENCE("driving licence"),
    UNDEFINED("undefined"),
}
