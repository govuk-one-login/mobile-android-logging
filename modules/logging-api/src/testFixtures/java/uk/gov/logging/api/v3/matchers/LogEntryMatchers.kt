package uk.gov.logging.api.v3.matchers

import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.MemorisedLogger
import uk.gov.logging.api.v3.customkey.CustomKey
import kotlin.reflect.KClass

@Suppress("TooManyFunctions")
object LogEntryMatchers {
    fun isBasicEntry(): Matcher<LogEntry> = CoreMatchers.instanceOf(LogEntry.Message::class.java)

    fun isErrorEntry(): Matcher<LogEntry> = CoreMatchers.instanceOf(LogEntry.Exception::class.java)

    fun isExceptionInstance(): Matcher<LogEntry> = CoreMatchers.instanceOf(LogEntry.Exception::class.java)

    fun hasLogEntry(matcher: Matcher<in Iterable<LogEntry>>): Matcher<MemorisedLogger> = HasLogEntry(matcher)

    fun isLogLevel(level: LogLevel): Matcher<LogEntry> = isLogLevel(CoreMatchers.equalTo(level))

    fun isLogLevel(matcher: Matcher<LogLevel>): Matcher<LogEntry> = IsLogLevel(matcher)

    fun hasMessage(expected: String): Matcher<LogEntry> = hasMessage(CoreMatchers.equalTo(expected))

    fun hasMessage(matcher: Matcher<String>): Matcher<LogEntry> = HasMessage(matcher)

    fun hasTag(expected: String): Matcher<LogEntry> = hasTag(CoreMatchers.equalTo(expected))

    fun hasTag(matcher: Matcher<String>): Matcher<LogEntry> = HasTag(matcher)

    fun hasException(klass: KClass<*>): Matcher<in LogEntry> =
        hasException(
            CoreMatchers.instanceOf(
                klass.java,
            ),
        )

    fun hasException(matcher: Matcher<in Throwable>): Matcher<in LogEntry> = HasThrowable(matcher)

    fun hasCustomKeys(matcher: Matcher<in CustomKey>): Matcher<in LogEntry> = HasCustomKeys(matcher)
}
