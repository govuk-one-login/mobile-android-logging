package uk.gov.logging.api.analytics.extensions

import java.util.stream.Stream
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class StringExtensionsKtTest {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("setupMd5Output")
    fun `Verify and output MD5 hashing`(
        stringToHash: String,
        expectedOutput: String
    ) {
        println("Expecting '$stringToHash' == '$expectedOutput'...")
        Assertions.assertEquals(expectedOutput, stringToHash.md5()) {
            "The MD5 Hash of '$stringToHash' should have matched '$expectedOutput'!"
        }
    }

    companion object {
        @JvmStatic
        fun setupMd5Output(): Stream<Arguments> = Stream.of(
            Arguments.arguments(
                "restart_desktop_journey_qr_code",
                "162f677e73d9f4696420d7ad574963a3"
            ),
            Arguments.arguments(
                "restart_mobile_journey",
                "3d02fab11a3c15ed0b6a4fd9974f1edb"
            ),
            Arguments.arguments(
                "restart_journey_error",
                "7c2abb8b5f60cd12782c07e97ced0d14"
            ),
            Arguments.arguments(
                "start_identity_at_govuk",
                "39a24255020d7153179bf15be2e7accc"
            ),
            Arguments.arguments(
                "anErrorCode",
                "bb80d37033d62c082bce748cca86c7ff"
            ),
            Arguments.arguments(
                "400_appinfo",
                "a086a40b12348c931c67254af62408c8"
            ),
            Arguments.arguments(
                "401_appinfo",
                "55ec4ba27512801b774e0f1a4a9dc3d4"
            ),
            Arguments.arguments(
                "403_appinfo",
                "b94f73484a36993028f0e11e6d513fc3"
            ),
            Arguments.arguments(
                "500_appinfo",
                "ff66ea65befe486adf43611cfdb9de79"
            ),
            Arguments.arguments(
                "400_camerapermission",
                "a15c77c44255e8a5f5bfaffb5ff8b9b7"
            ),
            Arguments.arguments(
                "500_android",
                "6cc6479c0f7ec116bfe3fc4f80438c88"
            )
        )
    }
}
