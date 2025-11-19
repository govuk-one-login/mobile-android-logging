package uk.gov.logging.api.performance

import java.net.URL

class FakePerformanceMonitor(
    val trace: Trace = Trace(),
    val httpMetric: HttpMetric = HttpMetric(),
) : PerformanceMonitor {
    override fun newTrace(name: String): PerformanceMonitor.Trace = trace

    override fun newHTTPMetric(
        url: URL,
        method: HttpMethod,
    ): PerformanceMonitor.HttpMetric = httpMetric

    class Trace : PerformanceMonitor.Trace {
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

    class HttpMetric : PerformanceMonitor.HttpMetric {
        override fun setRequestSize(bytes: Long) = Unit

        override fun setResponseSize(bytes: Long) = Unit

        override fun setResponseCode(code: Int) = Unit

        override fun setContentType(type: String) = Unit

        override fun stop() = Unit
    }
}
