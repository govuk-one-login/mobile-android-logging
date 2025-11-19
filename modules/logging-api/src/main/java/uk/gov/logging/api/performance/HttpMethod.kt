package uk.gov.logging.api.performance

/**
 * Represents standard HTTP methods supported by Firebase Performance Monitoring.
 *
 * Using this enum ensures type safety and prevents errors when referencing
 * FirebasePerformance.HttpMethod values.
 */
enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
    OPTIONS,
    TRACE,
    CONNECT,
}
