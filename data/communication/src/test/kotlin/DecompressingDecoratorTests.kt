import io.github.mmolosay.datalayercommunication.data.communication.convertion.decode.DecompressingDecorator
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.asString
import io.kotest.matchers.shouldBe
import org.junit.Test

class DecompressingDecoratorTests {

    @Test
    fun `decompresses correctly`() {
        val decoder = DecompressingDecorator(FakeDecoder())
        val compressed = Data(
            byteArrayOf(
                31,
                -117,
                8,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                -53,
                77,
                45,
                46,
                78,
                76,
                79,
                85,
                40,
                -55,
                87,
                72,
                -51,
                75,
                -50,
                79,
                73,
                5,
                0,
                -98,
                109,
                -95,
                -56,
                17,
                0,
                0,
                0
            )
        )

        val result = decoder.decode(compressed)

        result shouldBe "message to encode"
    }

    class FakeDecoder : Decoder<String> {
        override fun decode(data: Data): String =
            data.asString()
    }
}