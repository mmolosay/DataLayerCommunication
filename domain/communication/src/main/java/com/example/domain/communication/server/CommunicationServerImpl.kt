package com.example.domain.communication.server

import com.example.domain.communication.model.Data
import com.example.domain.communication.convertion.RequestDecoder
import com.example.domain.communication.convertion.ResponseEncoder

/**
 * Implementation of [CommunicationServer], employing request decoding and response encoding.
 */
class CommunicationServerImpl(
    private val decoder: RequestDecoder,
    private val encoder: ResponseEncoder,
    private val responseServer: ResponseServer,
) : CommunicationServer {

    override suspend fun on(request: Data): Data {
        val decoded = decoder.decode(request)
        val response = responseServer.on(decoded)
        return encoder.encode(response)
    }
}