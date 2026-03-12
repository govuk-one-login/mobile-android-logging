package uk.gov.logging.api.v2.errorKeys

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue


internal class ErrorKeysTest {

    @Test
    fun `Test string error keys returns correct key and value`() {

        val stringErrorKey = ErrorKeys.StringKey(KEY, STRING_VALUE)
        assertTrue { stringErrorKey.key == KEY }
        assertTrue { stringErrorKey.value == STRING_VALUE }
    }

    @Test
    fun `Test integer error keys returns correct key and value`() {
        val intErrorKey = ErrorKeys.IntKey(KEY, INT_VALUE)
        assertTrue { intErrorKey.key == KEY }
        assertTrue { intErrorKey.value == INT_VALUE }
    }

    @Test
    fun `Test double error keys returns correct key and value`(){
        val doubleErrorKey = ErrorKeys.DoubleKey(KEY,DOUBLE_VALUE)
        assertTrue { doubleErrorKey.key == KEY }
        assertTrue { doubleErrorKey.value == DOUBLE_VALUE }

    }

    @Test
    fun `Test boolean error keys returns correct key and value`(){
        val booleanErrorKey = ErrorKeys.BooleanKey(KEY,BOOLEAN_VALUE)
        assertTrue { booleanErrorKey.key == KEY }
        assertTrue { booleanErrorKey.value }

    }



    companion object{
        private const val KEY = "key"
        private const  val STRING_VALUE = "value"
        private const val  INT_VALUE = 1

        private const val DOUBLE_VALUE = 1.0

        private const val BOOLEAN_VALUE = true
    }

}
