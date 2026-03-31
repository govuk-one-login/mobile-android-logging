package uk.gov.logging.impl.analytics.v3

import com.google.firebase.analytics.FirebaseAnalytics
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.parameters.RequiredParameters
import uk.gov.logging.testdouble.v3.SystemLogger
import java.util.stream.Stream

internal class FirebaseAnalyticsLoggerTest {
    private var analytics: FirebaseAnalytics = mock()

    private var logger = SystemLogger()

    private val analyticsLogger by lazy {
        FirebaseAnalyticsLogger(
            analytics = analytics,
            logger = logger,
        )
    }

    @BeforeEach
    fun setup() {
        analytics = mock()
        logger = SystemLogger()
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("setupLogEventEdgeCases")
    fun `screen view events are Logged via Firebase`() {
        analyticsLogger.logEvent(true, event)

        verify(analytics, times(1)).logEvent(eq(event.eventType), any())
    }

    companion object {
        private val event =
            AnalyticsEvent.screenView(
                RequiredParameters(
                    digitalIdentityJourney = "",
                    journeyType = "driving licence",
                ),
            )

        @JvmStatic
        fun setupLogEventEdgeCases(): Stream<Arguments> =
            Stream.of(
                arguments(
                    named(
                        "Fails due to disabled permission",
                        false,
                    ),
                    event,
                ),
            )
    }
}
