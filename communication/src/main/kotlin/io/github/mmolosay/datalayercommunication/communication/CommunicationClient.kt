package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.models.Destination
import io.github.mmolosay.datalayercommunication.communication.models.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.models.rpc.response.Response
import io.github.mmolosay.datalayercommunication.utils.resource.Resource

/**
 * Exposes an API for components to communicate with [Destination]s in different ways.
 */
interface CommunicationClient {

    /**
     * Executes asynchronous request to [destination] with specified [request].
     */
    suspend fun <R : Response> request(
        destination: Destination,
        request: Request,
    ): Resource<R>
}