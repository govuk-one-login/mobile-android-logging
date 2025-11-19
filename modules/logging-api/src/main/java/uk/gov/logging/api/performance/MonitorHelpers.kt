package uk.gov.logging.api.performance

inline fun <T> PerformanceMonitor.measure(
    name: String,
    attributes: Map<String, String> = emptyMap(),
    block: () -> T,
): T {
    val trace = newTrace(name)
    attributes.forEach { (key, value) -> trace.putAttribute(key, value) }
    return try {
        block()
    } finally {
        trace.stop()
    }
}

suspend inline fun <T> PerformanceMonitor.measureSuspend(
    name: String,
    attributes: Map<String, String> = emptyMap(),
    crossinline block: suspend () -> T,
): T {
    val trace = newTrace(name)
    attributes.forEach { (key, value) -> trace.putAttribute(key, value) }
    return try {
        block()
    } finally {
        trace.stop()
    }
}
