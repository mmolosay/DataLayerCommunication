import fakes.FakeDecoder
import fakes.FakeEncoder
import io.github.mmolosay.datalayercommunication.data.communication.convertion.decode.DecompressingDecorator
import io.github.mmolosay.datalayercommunication.data.communication.convertion.encode.CompressingDecorator
import io.kotest.matchers.shouldBe
import org.junit.Test

class CompressingDecompressingDecoratorsTests {

    @Test
    fun `compresses and decompresses correctly`() {
        val initialValue = "my message string"
        val encoder = CompressingDecorator(FakeEncoder())
        val decoder = DecompressingDecorator(FakeDecoder())

        val encoded = encoder.encode(initialValue)
        val decoded = decoder.decode(encoded)

        decoded shouldBe initialValue
    }
}