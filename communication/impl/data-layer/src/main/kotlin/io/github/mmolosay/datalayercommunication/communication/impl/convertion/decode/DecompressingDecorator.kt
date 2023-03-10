package io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode

import io.github.mmolosay.datalayercommunication.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.communication.models.Data
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream

/**
 * [Decoder] with data decompressing functionality, utilizing GZIP file format.
 */
class DecompressingDecorator<T>(
    decoder: Decoder<T>
) : DecoderDecorator<T>(decoder) {

    override fun decode(data: Data): T {
        val uncompressed = GZIPInputStream(data.bytes.inputStream())
            .bufferedReader(StandardCharsets.UTF_8)
            .use { it.readText() }
        val bytes = uncompressed.toByteArray()
        return decoder.decode(Data(bytes))
    }
}