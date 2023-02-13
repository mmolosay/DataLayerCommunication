package io.github.mmolosay.datalayercommunication.data.communication

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.server.CommunicationServer
import io.github.mmolosay.datalayercommunication.domain.communication.server.ResponseServer

/**
 * Implementation of [CommunicationServer], employing request decoding and response encoding.
 */
class ConvertingCommunicationServer(
    private val decoder: RequestDecoder,
    private val encoder: ResponseEncoder,
    private val responseServer: ResponseServer,
) : CommunicationServer {

    override suspend fun reciprocate(request: Data): Data {
        val decoded = decoder.decode(request)
        val response = responseServer.reciprocate(decoded)
        return encoder.encode(response)
    }
}