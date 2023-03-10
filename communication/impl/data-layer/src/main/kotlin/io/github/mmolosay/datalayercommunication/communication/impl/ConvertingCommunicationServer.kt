package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.rpc.RequestDecoder
import io.github.mmolosay.datalayercommunication.communication.rpc.ResponseEncoder
import io.github.mmolosay.datalayercommunication.communication.server.CommunicationServer
import io.github.mmolosay.datalayercommunication.communication.server.ResponseServer

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