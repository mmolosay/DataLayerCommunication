package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.impl.convertion.Converters
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.ConvertersFactory.Feature
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.ConvertersFactory.add
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.DecompressingDecorator
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.CompressingDecorator
import io.github.mmolosay.datalayercommunication.communication.impl.fakes.FakeDecoder
import io.github.mmolosay.datalayercommunication.communication.impl.fakes.FakeEncoder
import io.kotest.matchers.should
import io.kotest.matchers.types.beOfType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ConvertersFactoryTests {

    @Test
    fun `adds Compression feature`() = runTest {
        val converters = makeBaseConverters()

        val compressionConverters = converters.add(Feature.Compression)

        compressionConverters.encoder should beOfType<CompressingDecorator<*>>()
        compressionConverters.decoder should beOfType<DecompressingDecorator<*>>()
    }

    private fun makeBaseConverters(): Converters<*> =
        Converters(
            encoder = FakeEncoder(),
            decoder = FakeDecoder(),
        )
}