package uk.gov.logging.api.v3.matchers

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import uk.gov.logging.api.v3.MemorisedLogger

internal class HasSize(
    private val matcher: Matcher<Int>,
) : TypeSafeMatcher<MemorisedLogger>() {
    override fun describeTo(description: Description?) {
        matcher.describeTo(description)
    }

    override fun describeMismatchSafely(
        item: MemorisedLogger?,
        mismatchDescription: Description?,
    ) {
        matcher.describeMismatch(item?.size, mismatchDescription)
    }

    override fun matchesSafely(item: MemorisedLogger?): Boolean = matcher.matches(item?.size)
}
