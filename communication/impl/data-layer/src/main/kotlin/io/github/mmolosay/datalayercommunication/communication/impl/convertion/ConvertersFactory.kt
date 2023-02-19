package io.github.mmolosay.datalayercommunication.communication.impl.convertion

import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.DecompressingDecorator
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.SerializationRequestDecoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.CompressingDecorator
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.SerializationRequestEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import kotlinx.serialization.StringFormat

object ConvertersFactory {

    fun <T> Converters<T>.add(
        vararg features: Feature,
    ): Converters<T> =
        make(
            baseEncoder = encoder,
            baseDecoder = decoder,
            compression = features.contains(Feature.Compression),
        )

    fun makeSerializationRequestConverters(
        format: StringFormat,
    ): RequestConverters =
        Converters(
            encoder = SerializationRequestEncoder(format),
            decoder = SerializationRequestDecoder(format),
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