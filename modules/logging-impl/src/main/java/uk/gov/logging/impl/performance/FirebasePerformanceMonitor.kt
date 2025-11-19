package uk.gov.logging.impl.performance

import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.HttpMetric
import com.google.firebase.perf.metrics.Trace
import uk.gov.logging.api.performance.HttpMethod
import uk.gov.logging.api.performance.PerformanceMonitor
import java.net.URL

typealias FirebaseTrace = Trace
typealias FirebaseHttpMetric = HttpMetric

class FirebasePerformanceMonitor(
    private val performance: FirebasePerformance,
) : PerformanceMonitor {
    override fun newTrace(name: String): PerformanceMonitor.Trace {
        val trace = performance.newTrace(name)
        trace.start()
        return Trace(trace)
    }

    override fun newHTTPMetric(
        url: URL,
        method: HttpMethod,
    ): PerformanceMonitor.HttpMetric {
        val metric = performance.newHttpMetric(url, method.name)
        metric.start()
        return HttpMetric(metric)
    }

    class Trace(
        private val delegate: FirebaseTrace,
    ) : PerformanceMonitor.Trace {
        override fun putAttribute(
            key: String,
            value: String,
        ) {
            delegate.putAttribute(key, value)
        }

        override fun incrementMetric(
            name: String,
            by: Long,
        ) {
            delegate.incrementMetric(name, by)
        }

        override fun stop() {
            delegate.stop()
        }
    }

    class HttpMetric(
        private val delegate: FirebaseHttpMetric,
    ) : PerformanceMonitor.HttpMetric {
        override fun setRequestSize(bytes: Long) {
            delegate.setRequestPayloadSize(bytes)
        }

        override fun setResponseSize(bytes: Long) {
            delegate.setResponsePayloadSize(bytes)
        }

        override fun setResponseCode(code: Int) {
            delegate.setHttpResponseCode(code)
        }

        override fun setContentType(type: String) {
            delegate.setResponseContentType(type)
        }

        override fun stop() {
            delegate.stop()
        }
    }
}
