package uk.gov.logging.api.v3.matchers

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import uk.gov.logging.api.v3.LogEntry

internal class HasTag(
    private val matcher: Matcher<String>,
) : TypeSafeMatcher<LogEntry>() {
    override fun describeTo(description: Description?) {
        matcher.describeTo(description)
    }

    override fun describeMismatchSafely(
        item: LogEntry?,
        mismatchDescription: Description?,
    ) {
        matcher.describeMismatch(item?.tag, mismatchDescription)
    }

    override fun matchesSafely(item: LogEntry?): Boolean = matcher.matches(item?.tag)
}
