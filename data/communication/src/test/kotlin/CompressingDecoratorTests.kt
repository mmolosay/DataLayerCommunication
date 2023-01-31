import io.github.mmolosay.datalayercommunication.data.communication.convertion.encode.CompressingDecorator
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.kotest.matchers.shouldBe
import org.junit.Test

class CompressingDecoratorTests {

    @Test
    fun `compresses correctly`() {
        val encoder = CompressingDecorator(FakeEncoder())
        val value = "message to encode"

        val result = encoder.encode(value)

        result.bytes shouldBe byteArrayOf(
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
    }

    class FakeEncoder : Encoder<String> {
        override fun encode(value: String): Data =
            Data.from(value)
    }
}