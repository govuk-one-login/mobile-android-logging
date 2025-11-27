package uk.gov.logging.impl.performance

import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.HttpMetric
import com.google.firebase.perf.metrics.Trace
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.willReturn
import uk.gov.logging.api.performance.HttpMethod
import java.net.URL
import kotlin.test.Test

class FirebasePerformanceMonitorTest {
    private val firebase = mock<FirebasePerformance>()
    private val firebaseTrace = mock<Trace>()
    private val firebaseHttpMetric = mock<HttpMetric>()
    private val monitor = FirebasePerformanceMonitor(firebase)

    @Test
    fun `newTrace starts the trace and returns wrapper`() {
        given { firebase.newTrace("foo") } willReturn { firebaseTrace }

        val trace = monitor.newTrace("foo")

        verify(firebase).newTrace("foo")
        verify(firebaseTrace).start()

        trace.putAttribute("k", "v")
        verify(firebaseTrace).putAttribute("k", "v")

        trace.incrementMetric("custom", 5)
        verify(firebaseTrace).incrementMetric("custom", 5)

        trace.stop()
        verify(firebaseTrace).stop()
    }

    @Test
    fun `newHttpMetric starts the trace and returns wrapper`() {
        val url = URL("https://www.example.com/testing/")
        given { firebase.newHttpMetric(url, HttpMethod.GET.name) } willReturn { firebaseHttpMetric }

        val httpMetric = monitor.newHTTPMetric(url, HttpMethod.GET)

        verify(firebase).newHttpMetric(url, HttpMethod.GET.name)
        verify(firebaseHttpMetric).start()

        httpMetric.setRequestSize(400L)
        verify(firebaseHttpMetric).setRequestPayloadSize(400L)

        httpMetric.setResponseSize(400L)
        verify(firebaseHttpMetric).setResponsePayloadSize(400L)

        httpMetric.setResponseCode(400)
        verify(firebaseHttpMetric).setHttpResponseCode(400)

        httpMetric.setContentType("text/plain")
        verify(firebaseHttpMetric).setResponseContentType("text/plain")

        httpMetric.stop()
        verify(firebaseHttpMetric).stop()
    }
}
