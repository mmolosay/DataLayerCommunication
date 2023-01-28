package io.github.mmolosay.datalayercommunication.data.communication.convertion

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.asString
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPOutputStream

abstract class EncoderDecorator<T>(
    protected val encoder: Encoder<T>
) : Encoder<T> {

    override fun encode(value: T): Data =
        encoder.encode(value)
}

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

class SerializationRequestEncoder : RequestEncoder {

    override fun encode(value: Request): Data {
        val json = Json.encodeToString(value)
        return Data.from(json)
    }
}

class SerializationResponseEncoder : ResponseEncoder {

    override fun encode(value: Response): Data {
        val json = Json.encodeToString(value)
        return Data.from(json)
    }
}