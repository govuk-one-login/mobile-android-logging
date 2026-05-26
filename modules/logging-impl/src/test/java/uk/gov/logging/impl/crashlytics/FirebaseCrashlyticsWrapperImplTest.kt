package uk.gov.logging.impl.crashlytics

import com.google.firebase.crashlytics.CustomKeysAndValues
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class FirebaseCrashlyticsWrapperImplTest {
    private val firebaseCrashlytics: FirebaseCrashlytics = mock()
    private val wrapper = FirebaseCrashlyticsWrapperImpl(firebaseCrashlytics)

    @Test
    fun `log delegates to firebase crashlytics`() {
        wrapper.log("message")

        verify(firebaseCrashlytics).log(eq("message"))
    }

    @Test
    fun `recordException delegates to firebase crashlytics`() {
        val throwable = RuntimeException("error")

        wrapper.recordException(throwable)

        verify(firebaseCrashlytics).recordException(eq(throwable))
    }

    @Test
    fun `recordException with keys delegates to firebase crashlytics`() {
        val throwable = RuntimeException("error")

        wrapper.recordException(throwable, mapOf("key" to "value"))

        verify(firebaseCrashlytics).recordException(eq(throwable), any<CustomKeysAndValues>())
    }
}
