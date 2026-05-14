package uk.gov.logging.api.v3.matchers

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import uk.gov.logging.api.v3.LogEntry

internal class HasThrowable(
    private val matcher: Matcher<in Throwable>,
) : TypeSafeMatcher<LogEntry>() {
    override fun describeTo(description: Description?) {
        matcher.describeTo(description)
    }

    override fun describeMismatchSafely(
        item: LogEntry?,
        mismatchDescription: Description?,
    ) {
        matcher.describeMismatch(
            (item as? LogEntry.Exception)?.throwable,
            mismatchDescription,
        )
    }

    override fun matchesSafely(item: LogEntry?): Boolean = matcher.matches((item as? LogEntry.Exception)?.throwable)
}
