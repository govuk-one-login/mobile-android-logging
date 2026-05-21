package uk.gov.logging.api.v3.matchers

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customkey.ErrorKeys

internal class HasErrorKeys(
    private val matcher: Matcher<in ErrorKeys>,
) : TypeSafeMatcher<LogEntry>() {
    override fun describeTo(description: Description?) {
        matcher.describeTo(description)
    }

    override fun describeMismatchSafely(
        item: LogEntry?,
        mismatchDescription: Description?,
    ) {
        matcher.describeMismatch(
            (item as? LogEntry.Exception)?.errorKeys,
            mismatchDescription,
        )
    }

    override fun matchesSafely(item: LogEntry?): Boolean = matcher.matches((item as? LogEntry.Exception)?.errorKeys)
}
