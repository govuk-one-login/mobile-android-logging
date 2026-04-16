package uk.gov.logging.api.v3

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import kotlin.reflect.KClass

object MemorisedLoggerMatchers {
    fun hasSize(expected: Int): Matcher<MemorisedLogger> = hasSize(equalTo(expected))

    fun hasSize(matcher: Matcher<Int>): Matcher<MemorisedLogger> = HasSize(matcher)
}

class HasSize(
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

@Suppress("TooManyFunctions")
object LogEntryMatchers {
    fun isBasicEntry(): Matcher<LogEntry> = instanceOf(LogEntry.Basic::class.java)

    fun isErrorEntry(): Matcher<LogEntry> = instanceOf(LogEntry.Error::class.java)

    fun isExceptionInstance(): Matcher<LogEntry> = instanceOf(LogEntry.WithException::class.java)

    fun isLogLevel(level: Int): Matcher<LogEntry> = isLogLevel(equalTo(level))

    fun isLogLevel(matcher: Matcher<Int>): Matcher<LogEntry> = IsLogLevel(matcher)

    fun hasMessage(expected: String): Matcher<LogEntry> = hasMessage(equalTo(expected))

    fun hasMessage(matcher: Matcher<String>): Matcher<LogEntry> = HasMessage(matcher)

    fun hasTag(expected: String): Matcher<LogEntry> = hasTag(equalTo(expected))

    fun hasTag(matcher: Matcher<String>): Matcher<LogEntry> = HasTag(matcher)

    fun hasException(klass: KClass<*>): Matcher<in LogEntry> = hasException(instanceOf(klass.java))

    fun hasException(matcher: Matcher<in Throwable>): Matcher<in LogEntry> = HasThrowable(matcher)
}

class HasThrowable(
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
            (item as? LogEntry.WithException)?.throwable,
            mismatchDescription,
        )
    }

    override fun matchesSafely(item: LogEntry?): Boolean = matcher.matches((item as? LogEntry.WithException)?.throwable)
}

class HasTag(
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

class HasMessage(
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

class IsLogLevel(
    private val matcher: Matcher<Int>,
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

class HasLogEntry(
    private val matcher: Matcher<Iterable<LogEntry>>,
) : TypeSafeMatcher<MemorisedLogger>() {
    override fun describeTo(description: Description?) {
        matcher.describeTo(description)
    }

    override fun describeMismatchSafely(
        item: MemorisedLogger?,
        mismatchDescription: Description?,
    ) {
        matcher.describeMismatch(item?.entries, mismatchDescription)
    }

    override fun matchesSafely(item: MemorisedLogger?): Boolean = matcher.matches(item?.entries)
}
