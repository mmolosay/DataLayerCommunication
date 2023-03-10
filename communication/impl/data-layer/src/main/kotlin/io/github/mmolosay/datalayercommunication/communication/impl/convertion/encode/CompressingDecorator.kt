package io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode

import io.github.mmolosay.datalayercommunication.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.models.asString
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPOutputStream

/**
 * [Encoder] with data compressing functionality, utilizing GZIP file format.
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