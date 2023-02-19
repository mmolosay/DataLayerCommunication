package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.DecompressingDecorator
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.CompressingDecorator
import io.github.mmolosay.datalayercommunication.communication.impl.fakes.FakeDecoder
import io.github.mmolosay.datalayercommunication.communication.impl.fakes.FakeEncoder
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