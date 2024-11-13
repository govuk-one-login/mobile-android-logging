package uk.gov.logging.testdouble

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import uk.gov.logging.api.CrashLogger
import javax.inject.Inject

class FakeCrashLogger @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : CrashLogger {
    private var logs = mutableListOf<Pair<String?, Throwable>>()

    val size: Int get() = logs.size

    fun any(condition: (Pair<String?, Throwable>) -> Boolean) = logs.any(condition)

    operator fun contains(message: String?): Boolean = logs.any { entry ->
        message == entry.first
    }

    operator fun contains(entry: Pair<String?, Throwable>): Boolean = logs.any { (
        message: String?, throwable: Throwable,
    ),
        ->
        entry.first == message && entry.second == throwable
    }

    operator fun get(i: Int): Pair<String?, Throwable> = this.logs[i]

    override fun log(throwable: Throwable) {
        this.logs.add(
            Pair(throwable.message, throwable),
        )

        Toast.makeText(
            context,
            throwable.message,
            Toast.LENGTH_SHORT,
        )?.show()
    }

    override fun log(message: String) {
        val exception = Throwable(message)
        log(exception)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FakeCrashLogger) return false

        if (logs != other.logs) return false

        return true
    }

    override fun hashCode(): Int {
        return logs.hashCode()
    }

    override fun toString(): String {
        return "FakeCrashLogger(size=$size, logs=$logs)"
    }
}
