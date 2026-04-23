package uk.gov.logging.api.v3.matchers

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.MemorisedLogger

object MemorisedLoggerMatchers {
    fun hasSize(expected: Int): Matcher<MemorisedLogger> = hasSize(equalTo(expected))

    fun hasSize(matcher: Matcher<Int>): Matcher<MemorisedLogger> = HasSize(matcher)
}

internal class HasMessage(
    private val matcher: Matcher<String>,
) : TypeSafeMatcher<LogEntry>() {
    override fun describeTo(description: Description?) {
        matcher.describeTo(description)
    }

    override fun describeMismatchSafely(
        item: LogEntry?,
        mismatchDescription: Description?,
    ) {
        matcher.describeMismatch(item?.message, mismatchDescription)
    }

    override fun matchesSafely(item: LogEntry?): Boolean = matcher.matches(item?.message)
}

internal class IsLogLevel(
    private val matcher: Matcher<LogLevel>,
) : TypeSafeMatcher<LogEntry>() {
    override fun describeTo(description: Description?) {
        matcher.describeTo(description)
    }

    override fun describeMismatchSafely(
        item: LogEntry?,
        mismatchDescription: Description?,
    ) {
        matcher.describeMismatch(item?.level, mismatchDescription)
    }

    override fun matchesSafely(item: LogEntry?): Boolean = matcher.matches(item?.level)
}
