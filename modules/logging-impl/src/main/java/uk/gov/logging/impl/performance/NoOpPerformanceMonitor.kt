package uk.gov.logging.impl.performance

import uk.gov.logging.api.performance.HttpMethod
import uk.gov.logging.api.performance.PerformanceMonitor
import java.net.URL

object NoOpPerformanceMonitor : PerformanceMonitor {
    override fun newTrace(name: String): PerformanceMonitor.Trace = Trace

    override fun newHTTPMetric(
        url: URL,
        method: HttpMethod,
    ): PerformanceMonitor.HttpMetric = HttpMetric

    object Trace : PerformanceMonitor.Trace {
        override fun putAttribute(
            key: String,
            value: String,
        ) = Unit

        override fun incrementMetric(
            name: String,
            by: Long,
        ) = Unit

        override fun stop() = Unit
    }

    object HttpMetric : PerformanceMonitor.HttpMetric {
        override fun setRequestSize(bytes: Long) = Unit

        override fun setResponseSize(bytes: Long) = Unit

        override fun setResponseCode(code: Int) = Unit

        override fun setContentType(type: String) = Unit

        override fun stop() = Unit
    }
}
