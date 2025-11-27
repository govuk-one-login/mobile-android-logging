package uk.gov.logging.api.performance

import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.net.URL
import kotlin.test.Test
import kotlin.test.assertTrue

class MonitorHelpersTest {
    private val trace: FakePerformanceMonitor.Trace = mock()
    private val monitor = FakePerformanceMonitor(trace)

    @Test
    fun `measure with attributes`() {
        val name = "Foo"
        val key = "k"
        val value = "v"
        val attributes = mapOf(key to value)
        val isRun = monitor.measure(name, attributes) { true }
        verify(trace).putAttribute(key, value)
        verify(trace).stop()
        assertTrue(isRun)
    }

    @Test
    fun `measure without attributes`() {
        val name = "Foo"
        val isRun = monitor.measure(name) { true }
        verify(trace).stop()
        verify(trace, times(0)).putAttribute(any(), any())

        assertTrue(isRun)
    }

    @Test
    fun `measureSuspend with attributes`() =
        runTest {
            val name = "Foo"
            val key = "k"
            val value = "v"
            val attributes = mapOf(key to value)
            val isRun = monitor.measureSuspend(name, attributes) { true }
            verify(trace).putAttribute(key, value)
            verify(trace).stop()
            assertTrue(isRun)
        }

    @Test
    fun `measureSuspend without attributes`() =
        runTest {
            val name = "Foo"
            val isRun = monitor.measureSuspend(name) { true }
            verify(trace).stop()
            verify(trace, times(0)).putAttribute(any(), any())
            assertTrue(isRun)
        }

    private class FakePerformanceMonitor(
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
}
