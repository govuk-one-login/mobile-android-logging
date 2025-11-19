package uk.gov.logging.impl.performance

import uk.gov.logging.api.performance.HttpMethod
import java.net.URL
import kotlin.test.Test

class NoOpPerformanceMonitorTest {
    private val monitor = NoOpPerformanceMonitor()

    @Test
    fun `newTrace starts the trace and returns NoOp wrapper`() {
        // Tests no crashes, can't check for NoOp
        val trace = monitor.newTrace("foo")

        trace.putAttribute("k", "v")

        trace.incrementMetric("custom", 5)

        trace.stop()
    }

    @Test
    fun `newHttpMetric starts the trace and returns NoOp wrapper`() {
        // Tests no crashes, can't check for NoOp
        val url = URL("https://www.example.com/testing/")
        val httpMetric = monitor.newHTTPMetric(url, HttpMethod.GET)

        httpMetric.setRequestSize(400L)

        httpMetric.setResponseSize(400L)

        httpMetric.setResponseCode(400)

        httpMetric.setContentType("text/plain")

        httpMetric.stop()
    }
}
