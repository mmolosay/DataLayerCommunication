package io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream

/**
 * [Encoder] with data decompressing functionality.
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