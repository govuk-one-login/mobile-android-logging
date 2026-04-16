package uk.gov.logging.api.v3.customkey

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CustomKeyTest {
    private lateinit var key: CustomKey

    @AfterEach
    fun verifyKey() {
        assertEquals(KEY, key.key)
    }

    @Test
    fun `Test string custom keys returns correct key and value`() {
        key = CustomKey.StringKey(KEY, STRING_VALUE)
        assertEquals(STRING_VALUE, key.value)
    }

    @Test
    fun `Test integer custom keys returns correct key and value`() {
        key = CustomKey.IntKey(KEY, INT_VALUE)

        assertEquals(INT_VALUE, key.value)
    }

    @Test
    fun `Test double custom keys returns correct key and value`() {
        key = CustomKey.DoubleKey(KEY, DOUBLE_VALUE)
        assertEquals(DOUBLE_VALUE, key.value)
    }

    @Test
    fun `Test boolean custom keys returns correct key and value`() {
        key = CustomKey.BooleanKey(KEY, BOOLEAN_VALUE)
        assertEquals(BOOLEAN_VALUE, key.value)
    }

    companion object {
        private const val KEY = "key"
        private const val STRING_VALUE = "value"
        private const val INT_VALUE = 1

        private const val DOUBLE_VALUE = 1.0

        private const val BOOLEAN_VALUE = true
    }
}
