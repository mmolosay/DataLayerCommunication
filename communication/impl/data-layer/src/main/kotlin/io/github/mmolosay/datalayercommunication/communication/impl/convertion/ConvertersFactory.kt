package io.github.mmolosay.datalayercommunication.communication.impl.convertion

import io.github.mmolosay.datalayercommunication.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.DecompressingDecorator
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.CompressingDecorator

object ConvertersFactory {

    fun <T> Converters<T>.add(
        vararg features: Feature,
    ): Converters<T> =
        make(
            baseEncoder = encoder,
            baseDecoder = decoder,
            compression = features.contains(Feature.Compression),
        )

    private fun <T> make(
        baseEncoder: Encoder<T>,
        baseDecoder: Decoder<T>,
        compression: Boolean,
    ): Converters<T> {
        val encoder = baseEncoder
            .let { if (compression) CompressingDecorator(it) else it }
        val decoder = baseDecoder
            .let { if (compression) DecompressingDecorator(it) else it }
        return Converters(encoder, decoder)
    }

    enum class Feature {
        Compression,
    }
}