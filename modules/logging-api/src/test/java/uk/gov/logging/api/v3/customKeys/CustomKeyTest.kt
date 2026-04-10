package uk.gov.logging.api.v3.customKeys

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class CustomKeyTest {
    @Test
    fun `Test string custom keys returns correct key and value`() {
        val stringErrorKey = CustomKey.StringKey(KEY, STRING_VALUE)
        assertTrue { stringErrorKey.key == KEY }
        assertTrue { stringErrorKey.value == STRING_VALUE }
    }

    @Test
    fun `Test integer custom keys returns correct key and value`() {
        val intErrorKey = CustomKey.IntKey(KEY, INT_VALUE)
        assertTrue { intErrorKey.key == KEY }
        assertTrue { intErrorKey.value == INT_VALUE }
    }

    @Test
    fun `Test double custom keys returns correct key and value`() {
        val doubleErrorKey = CustomKey.DoubleKey(KEY, DOUBLE_VALUE)
        assertTrue { doubleErrorKey.key == KEY }
        assertTrue { doubleErrorKey.value == DOUBLE_VALUE }
    }

    @Test
    fun `Test boolean custom keys returns correct key and value`() {
        val booleanErrorKey = CustomKey.BooleanKey(KEY, BOOLEAN_VALUE)
        assertTrue { booleanErrorKey.key == KEY }
        assertTrue { booleanErrorKey.value }
    }

    companion object {
        private const val KEY = "key"
        private const val STRING_VALUE = "value"
        private const val INT_VALUE = 1

        private const val DOUBLE_VALUE = 1.0

        private const val BOOLEAN_VALUE = true
    }
}
