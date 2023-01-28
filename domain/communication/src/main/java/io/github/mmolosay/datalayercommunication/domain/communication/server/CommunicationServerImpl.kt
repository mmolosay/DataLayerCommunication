package io.github.mmolosay.datalayercommunication.domain.communication.server

import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseEncoder

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