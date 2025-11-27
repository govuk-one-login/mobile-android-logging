package uk.gov.logging.api.performance

import java.net.URL

interface PerformanceMonitor {
    fun newTrace(name: String): Trace

    fun newHTTPMetric(
        url: URL,
        method: HttpMethod,
    ): HttpMetric

    interface Trace {
        fun putAttribute(
            key: String,
            value: String,
        )

        fun incrementMetric(
            name: String,
            by: Long = 1L,
        )

        fun stop()
    }

    interface HttpMetric {
        fun setRequestSize(bytes: Long)

        fun setResponseSize(bytes: Long)

        fun setResponseCode(code: Int)

        fun setContentType(type: String)

        fun stop()
    }
}
