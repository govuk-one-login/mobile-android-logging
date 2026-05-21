package uk.gov.logging.api.v3.matchers

import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.MemorisedLogger
import uk.gov.logging.api.v3.customkey.ErrorKeys

@Suppress("TooManyFunctions")
object LogEntryMatchers {
    fun isMessageEntry(): Matcher<LogEntry> = CoreMatchers.instanceOf(LogEntry.Message::class.java)

    fun isExceptionInstance(): Matcher<LogEntry> = CoreMatchers.instanceOf(LogEntry.Exception::class.java)

    fun hasLogEntry(matcher: Matcher<in Iterable<LogEntry>>): Matcher<MemorisedLogger> = HasLogEntry(matcher)

    fun isLogLevel(level: LogLevel): Matcher<LogEntry> = isLogLevel(CoreMatchers.equalTo(level))

    fun isLogLevel(matcher: Matcher<LogLevel>): Matcher<LogEntry> = IsLogLevel(matcher)

    fun hasMessage(expected: String): Matcher<LogEntry> = hasMessage(CoreMatchers.equalTo(expected))

    fun hasMessage(matcher: Matcher<String>): Matcher<LogEntry> = HasMessage(matcher)

    fun hasTag(expected: String): Matcher<LogEntry> = hasTag(CoreMatchers.equalTo(expected))

    fun hasTag(matcher: Matcher<String>): Matcher<LogEntry> = HasTag(matcher)

    fun hasException(matcher: Matcher<in Throwable>): Matcher<in LogEntry> = HasThrowable(matcher)

    fun hasErrorKeys(matcher: Matcher<in ErrorKeys>): Matcher<in LogEntry> = HasErrorKeys(matcher)
}
