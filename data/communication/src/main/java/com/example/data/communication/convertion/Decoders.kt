package com.example.data.communication.convertion

import com.example.domain.communication.convertion.Decoder
import com.example.domain.communication.convertion.RequestDecoder
import com.example.domain.communication.convertion.ResponseDecoder
import com.example.domain.communication.model.Data
import com.example.domain.communication.model.asString
import com.example.domain.communication.model.request.Request
import com.example.domain.communication.model.response.Response
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream

abstract class DecoderDecorator<T>(
    protected val decoder: Decoder<T>
) : Decoder<T> {

    override fun decode(data: Data): T =
        decoder.decode(data)
}

class UncompressingDecorator<T>(
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

class SerializationRequestDecoder : RequestDecoder {

    override fun decode(data: Data): Request {
        val json = data.asString()
        return Json.decodeFromString(json)
    }
}

class SerializationResponseDecoder : ResponseDecoder {

    override fun decode(data: Data): Response {
        val json = data.asString()
        return Json.decodeFromString(json)
    }
}