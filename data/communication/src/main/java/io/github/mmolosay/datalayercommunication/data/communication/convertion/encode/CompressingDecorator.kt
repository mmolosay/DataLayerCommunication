package io.github.mmolosay.datalayercommunication.data.communication.convertion.encode

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.asString
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPOutputStream

/**
 * [Encoder] with data compressing functionality.
 */
class CompressingDecorator<T>(
    encoder: Encoder<T>
) : EncoderDecorator<T>(encoder) {

    override fun encode(value: T): Data {
        val encoded = encoder.encode(value)
        val string = encoded.asString()
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos)
            .bufferedWriter(StandardCharsets.UTF_8)
            .use { it.write(string) }
        val bytes = bos.toByteArray()
        return Data(bytes)
    }
}